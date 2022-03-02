package com.example.movielist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.movielist.models.module
import com.example.movielist.viewmodel.MovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("module", "$movieViewModel")
        /*
        val koinStarter = object {
            @Synchronized
            fun start(context: Context): Koin {
                return GlobalContext.getOrNull() ?: startKoin {
                    androidContext(context.applicationContext)
                    modules(module)
                }.koin
            }
        }
        koinStarter.start(this)
        */
    }
}