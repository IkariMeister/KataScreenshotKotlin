package com.karumi.ui.presenter

import arrow.core.Either
import co.metalab.asyncawait.async
import com.karumi.common.weak
import com.karumi.domain.model.*
import com.karumi.domain.usecase.GetSuperHeroByName
import com.karumi.ui.LifecycleSubscriber

class SuperHeroDetailPresenter(
        view: View,
        private val getSuperHeroByName: GetSuperHeroByName) :
        LifecycleSubscriber {

    private val view: View? by weak(view)

    private lateinit var name: String

    fun preparePresenter(name: String?) {
        if (name != null) {
            this.name = name
        } else {
            view?.close()
        }
    }

    override fun update() {
        view?.showLoading()
        refreshSuperHeroes()
    }

    private fun refreshSuperHeroes() = async {
        val result = await { getSuperHeroByName(name) }
        view?.hideLoading()
        when(result){
            is Either.Right -> view?.showSuperHero(result.b)
            is Either.Left ->
                when(result.a){
                is SuperHeroNotFound -> view?.showSuperHeroNotFound()
                is NoInternetConnection -> view?.showNoInternetConnection()

            }
        }
    }

    interface View {
        fun close()
        fun showLoading()
        fun hideLoading()
        fun showSuperHero(superHero: SuperHero)
        fun showNoInternetConnection()
        fun showSuperHeroNotFound()
    }

}