package com.masselis.portfolio.ui.utils

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
public expect annotation class CommonParcelize()

public expect interface CommonParcelable

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
public expect annotation class CommonIgnoredOnParcel()
