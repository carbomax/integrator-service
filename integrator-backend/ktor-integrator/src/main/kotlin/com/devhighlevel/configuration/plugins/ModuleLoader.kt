package com.devhighlevel.configuration.plugins

import com.devhighlevel.users.infrastructure.UserMongoRepository
import com.devhighlevel.users.application.UserCommandHandler
import com.devhighlevel.users.domain.repository.UserRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.dsl.single

object ModuleLoader {
    fun init(){
        startKoin {
            modules(
                listOf(usersModule())
            )
        }
    }

    private fun usersModule(): Module {
        return module {
            single<UserRepository>{ UserMongoRepository() }
            single<UserMongoRepository>()
            single<UserCommandHandler>()
        }
    }
}