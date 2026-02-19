package com.masselis.portfolio.ui.utils

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
public actual annotation class CommonParcelize

public actual interface CommonParcelable

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
public actual annotation class CommonIgnoredOnParcel
