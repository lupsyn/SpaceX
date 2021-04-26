package com.challenge.core.android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory


class InjectingFragmentFactory constructor(
    private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Fragment>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val creator = creators[fragmentClass]
            ?: return createFragmentAsFallback(classLoader, className)

        try {
            return creator
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun createFragmentAsFallback(classLoader: ClassLoader, className: String): Fragment {
        return super.instantiate(classLoader, className)
    }
}