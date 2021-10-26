package com.devhiglevel.integratorservice.services.upload

import com.devhiglevel.integratorservice.models.documents.MeliProduct
import com.devhiglevel.integratorservice.models.documents.Pictures
import com.devhiglevel.integratorservice.services.ProductService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.collect
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class UploadService(private val productService: ProductService) {


    @Value("\${upload.store.path}")
    private val basePath: String = ""

    suspend fun uploadProductImages(files: Flux<FilePart>, productId: String): MeliProduct {
        val productFound = productService.findById(productId)
        val result = files.map { it }.asFlow().toList()

            .map {
                withContext(Dispatchers.IO) {
                    async(CoroutineName(it.filename() + UUID.randomUUID().toString())) {

                        kotlin.run {
                            val fileName = UUID.randomUUID().toString().plus(".png")
                            val picture = Pictures(fileName)
                            it.transferTo(
                                Paths.get(basePath).resolve("products").resolve(fileName).toAbsolutePath()
                            ).collect { }
                            picture
                        }

                    }
                }
            }.awaitAll()

        productFound.pictures = productFound.pictures?.plus(result)
        return productService.update(productFound)
    }

    fun download(resource: String, fileName: String): ByteArray {
        val path = getPath(resource, fileName)
        return Files.readAllBytes(path)
    }

    suspend fun removeFiles(resource: String, fileNames: List<String>, entityId: String): List<Pair<String, Boolean>> {
        val productFound = productService.findById(entityId)

        return fileNames.map {
            withContext(Dispatchers.IO) {
                async {
                    val deleted = Files.deleteIfExists(Path.of(basePath).resolve(resource).resolve(it))
                    when {
                        deleted && !productFound.pictures.isNullOrEmpty() -> {
                            val (_, replace) = productFound.pictures!!.partition { pictures -> pictures.source == it }
                            productFound.pictures = replace
                            productService.update(productFound)

                        }
                    }
                    Pair(it, deleted)
                }
            }
        }.awaitAll()
    }


    private fun getPath(resource: String, fileName: String): Path {
        val filePAth = Paths.get(basePath).resolve(resource).resolve(fileName)
        if (!Files.exists(filePAth)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "File $fileName not found")
        }
        return filePAth
    }

}