package com.karumi.ui.view

import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.karumi.data.repository.SuperHeroRepository
import com.karumi.domain.model.SuperHero
import com.karumi.mockito.MockitoExtensions.on
import org.junit.Test
import org.mockito.Mock

class MainActivityTest : AcceptanceTest<MainActivity>(MainActivity::class.java) {

    @Mock private lateinit var repository: SuperHeroRepository

    @Test
    fun showsEmptyCaseIfThereAreNoSuperHeroes() {
        givenThereAreNoSuperHeroes()

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsSomeSuperHeroesIfThereAreSomeSuperHeroes(){
        givenThereAreSomeSuperHeroes(5)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsAvengersBadgeIfThereAreSomeAvengers(){
        givenThereAreSomeSuperHeroes(5,true)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun notShowsAvengersBadgeIfThereAreNoAvengers(){
        givenThereAreSomeSuperHeroes(5,false)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsNameIfTheNameIsTooLong(){
        givenThereAreSomeSuperHeroes(basename = "I am a really long super hero with a great cool name - ")

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsNameAndAvengerBadgeIfTheNameIsTooLongAndIsAvenger(){
        givenThereAreSomeSuperHeroes(basename = "I am a really long super hero with a great cool name - ",avengers = true)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsEmptyNameIfTheNameIsEmpty(){
        givenThereAreSomeSuperHeroes(basename = "")

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsEmptyNameAndAvengerBadgeIfTheNameIsEmptyAndIsAvenger(){
        givenThereAreSomeSuperHeroes(basename = "",avengers = true)

        val activity = startActivity()

        compareScreenshot(activity)
    }



    private fun givenThereAreSomeSuperHeroes(
            numberOfSuperHeroes: Int = 1,
            avengers: Boolean = false,
            basename : String = "SuperHero - "): List<SuperHero> {
        val superHeroes = IntRange(0, numberOfSuperHeroes - 1).map { id ->
            val superHeroName
            = if(id!=0) basename + id else basename
            val superHeroDescription = "Description Super Hero - $id"
            SuperHero(superHeroName, null, avengers,
                    superHeroDescription)
        }

        on(repository.getAllSuperHeroes()).thenReturn(superHeroes)
        return superHeroes
    }



    private fun givenThereAreNoSuperHeroes() {
        on(repository.getAllSuperHeroes()).thenReturn(emptyList())
    }

    override val testDependencies = Module(allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }
}