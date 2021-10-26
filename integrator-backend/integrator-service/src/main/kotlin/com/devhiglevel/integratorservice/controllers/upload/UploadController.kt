package com.devhiglevel.integratorservice.controllers.upload

import com.devhiglevel.integratorservice.models.documents.MeliProduct
import com.devhiglevel.integratorservice.services.upload.UploadService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.util.MimeTypeUtils.*
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/upload")
class UploadController(val uploadService: UploadService) {

    @PostMapping("products/{id}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun productImage(
        @RequestPart("files") file: Flux<FilePart>,
        @PathVariable id: String
    ): ResponseEntity<Mono<MeliProduct>> {
        return ResponseEntity(
            Mono.justOrEmpty(uploadService.uploadProductImages(file, productId = id)),
            HttpStatus.CREATED
        )

    }



    @GetMapping("download/{resource}")
    suspend fun downloadImage(
        @PathVariable resource: String,
        @RequestParam fileName: String
    ): ResponseEntity<Mono<ByteArray>> {
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(IMAGE_PNG_VALUE))
            .body(Mono.justOrEmpty(uploadService.download(resource, fileName)))

    }

    @DeleteMapping("remove/{resource}/{id}")
    suspend fun removeFile(
        @PathVariable resource: String,
        @RequestParam fileName: List<String>,
        @PathVariable id: String
    ): ResponseEntity<Mono<List<Pair<String, Boolean>>>> {
        return ResponseEntity(Mono.justOrEmpty(uploadService.removeFiles(resource, fileName, id)), HttpStatus.OK)

    }
}