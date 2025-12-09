package com.promtuz.chat.di

import com.promtuz.chat.data.local.dao.UserDao
import com.promtuz.chat.data.repository.UserRepository
import com.promtuz.chat.security.KeyManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repoModule = module {
    singleOf(::UserRepository)
}