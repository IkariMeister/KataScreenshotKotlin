package com.karumi.domain.usecase

import arrow.core.Either
import com.karumi.data.repository.SuperHeroRepository
import com.karumi.domain.model.Errors
import com.karumi.domain.model.SuperHero

class GetSuperHeroByName(private val superHeroesRepository: SuperHeroRepository) {

    operator fun invoke(name: String): Either<Errors, SuperHero> = superHeroesRepository.getByName(name)
}