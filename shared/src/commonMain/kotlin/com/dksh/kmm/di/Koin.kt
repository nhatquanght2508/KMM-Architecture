package com.dksh.kmm.di

import com.dksh.kmm.data_cache.CacheDataImp
import com.dksh.kmm.data_cache.sqldelight.SharedDatabase
import com.dksh.kmm.data_remote.RemoteDataImp
import com.dksh.kmm.data_remote.model.mapper.ApiToDoMapper
import com.dksh.kmm.domain.IRepository
import com.dksh.kmm.domain.interactors.GetToDoUseCase
import com.dksh.kmm.domain.interactors.GetTodoFavoritesUseCase
import com.dksh.kmm.domain.interactors.GetToDoListUseCase
import com.dksh.kmm.domain.interactors.IsToDoFavoriteUseCase
import com.dksh.kmm.domain.interactors.SwitchToDoFavoriteUseCase
import com.dksh.kmm.repository.ICacheData
import com.dksh.kmm.repository.IRemoteData
import com.dksh.kmm.repository.RepositoryImp
import com.dksh.kmm.presentation.ui.features.todo_detail.ToDoDetailViewModel
import com.dksh.kmm.presentation.ui.features.todos.ToDoViewModel
import com.dksh.kmm.presentation.ui.features.todo_favorites.ToDoFavoritesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            ktorModule,
            sqlDelightModule,
            mapperModule,
            dispatcherModule,
            platformModule()
        )
    }

val viewModelModule = module {
    factory { ToDoViewModel(get()) }
    factory { ToDoFavoritesViewModel(get()) }
    factory { params -> ToDoDetailViewModel(get(), get(), get(), params.get()) }
}

val useCasesModule: Module = module {
    factory { GetToDoListUseCase(get(), get()) }
    factory { GetTodoFavoritesUseCase(get(), get()) }
    factory { GetToDoUseCase(get(), get()) }
    factory { IsToDoFavoriteUseCase(get(), get()) }
    factory { SwitchToDoFavoriteUseCase(get(), get()) }
}

val repositoryModule = module {
    single<IRepository> { RepositoryImp(get(), get()) }
    single<ICacheData> { CacheDataImp(get()) }
    single<IRemoteData> { RemoteDataImp(get(), get(), get()) }


}

val ktorModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single { "https://jsonplaceholder.typicode.com" }
}

val sqlDelightModule = module {
    single { SharedDatabase(get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val mapperModule = module {
    factory { ApiToDoMapper() }
}

fun initKoin() = initKoin {}



