package com.karumi.ui.view

import android.os.Bundle
import arrow.core.Either
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.karumi.data.repository.SuperHeroRepository
import com.karumi.domain.model.*
import com.karumi.mockito.MockitoExtensions
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock

class SuperheroDetailActivityTest : AcceptanceTest<SuperHeroDetailActivity>(SuperHeroDetailActivity::class.java) {

    @Mock
    private lateinit var repository: SuperHeroRepository

    @Test
    fun showsSuperHeroIfIsARegularSuperhero(){
        val hero = givenThereAreSomeSuperHeroes()

        val activity = startActivity(hero)

        compareScreenshot(activity)
    }

    @Test
    fun showsAvengerIfIsAvenger(){
        val hero = givenThereAreSomeSuperHeroes(avengers = true)

        val activity = startActivity(hero)

        compareScreenshot(activity)
    }

//    @Test
//    fun showsSuperHeroNotFoundIfSuperHeroIsNotFound(){
//        givenSuperHeroNotFound()
//
//        val activity = startActivity()
//
//        compareScreenshot(activity)
//    }

    private fun startActivity(superHero: SuperHero): SuperHeroDetailActivity {
        val args = Bundle()
        args.putString("super_hero_name_key", superHero.name)
        return startActivity(args)
    }

    private fun givenThereAreSomeSuperHeroes(
            avengers: Boolean = false,
            superHeroName : String = "SuperHero - ",
            superHeroDescription : String = "Description Super Hero "): SuperHero {

            val superHero = SuperHero(superHeroName, null, avengers,
                    superHeroDescription)

        MockitoExtensions.on(repository.getByName(anyString())).thenReturn(Either.Right(superHero))
        return superHero
    }

    fun givenSuperHeroNotFound(){
        MockitoExtensions.on(repository.getByName(anyString())).thenReturn(Either.Left(SuperHeroNotFound))
    }

    override val testDependencies = Kodein.Module(allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }
}