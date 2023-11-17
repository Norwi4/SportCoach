/*
 * Copyright (c) 2023 FlyDrone.
 */

package com.techsolutions.sportcoach

import org.jooq.Condition
import org.jooq.Field
import org.jooq.Record1
import org.jooq.Select
import org.jooq.impl.DSL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime

/**
 *
 * @author Maxim Nikolsky
 */

/*
=================================================
  Вспомогательные методы для построения условий
=================================================
 */

/**
 * Создать условие равенства поля и значения. Если значение, c которым сравнивается поле, равно null, то условие не добавляется
 *
 * @param field поле
 * @param value значение
 * @param T тип значения
 * @return Условие
 */
fun <T> eq(field: Field<T?>, value: T?) = value?.let { field.eq(it) } ?: DSL.noCondition()

/**
 * Создать условие равенства поля результату выполнения запроса
 *
 * @param field поле
 * @param select запрос
 * @param T тип возвращаемого запросом значения
 * @return Условие
 */
fun <T> eq(field: Field<T?>, select: Select<out Record1<T?>>) = field.eq(select)

/**
 * Создать условие равенства поля и значения. Если значение, с которым сравнивается поле,
 * равно null, то добавится условие - поле is null
 *
 * @param field поле
 * @param value значение
 * @param T тип значения
 * @return Условие
 */
fun <T> eqOrNull(field: Field<T?>, value: T?) =
    if (value != null) {
        field.eq(value)
    } else {
        field.isNull
    }

/**
 * Создать условие равенства для поля, игнорируя регистр.
 * Если значение, с которым сравнивается поле, равно null, то условие не добавляется
 *
 * @param field поле
 * @param value значение
 * @return Условие
 */
fun eqIgnoreCase(field: Field<String?>, value: String?) =
    value?.let { field.equalIgnoreCase(it) } ?: DSL.noCondition()

/**
 * Создать условие неравенства поля и значения. Если значение, c которым сравнивается поле, равно null, то условие не добавляется
 *
 * @param field поле
 * @param value значение
 * @param T тип значения
 * @return Условие
 */
fun <T> ne(field: Field<T?>, value: T?) = value?.let { field.ne(it) } ?: DSL.noCondition()

/**
 * Создать условие ilike для строкового поля. Если значение, с которым сравнивается поле, равно null, то условие не добавляется
 * @param field поле
 * @param value значение
 * @return Условие
 */
fun ilike(field: Field<String?>, value: String?) =
    value?.let { field.likeIgnoreCase("%$it%") } ?: DSL.noCondition()

/**
 * Добавить условие in
 *
 * @param field поле
 * @param values значения
 * @param T тип значения поля
 * @return Условие
 */
fun <T> `in`(field: Field<T?>, values: Collection<T?>?) =
    if (!values.isNullOrEmpty()) {
        field.`in`(values)
    } else {
        DSL.noCondition()
    }

/**
 * Добавить условие in
 *
 * @param field поле
 * @param values значения
 * @param T тип значения поля
 * @return Условие
 */
fun <T> `in`(field: Field<T?>, vararg values: T?) =
    if (values.isNotEmpty()) {
        field.`in`(*values)
    } else {
        DSL.noCondition()
    }

/**
 * Добавить условие notIn
 *
 * @param field поле
 * @param values значения
 * @param T тип значения поля
 * @return Условие
 */
fun <T> notIn(field: Field<T?>, values: Collection<T?>?) =
    if (!values.isNullOrEmpty()) {
        field.notIn(values)
    } else {
        DSL.noCondition()
    }

/**
 * Добавить условие notIn
 *
 * @param field поле
 * @param values значения
 * @param T тип значения поля
 * @return Условие
 */
fun <T> notIn(field: Field<T?>, vararg values: T?) =
    if (values.isNotEmpty()) {
        field.notIn(*values)
    } else {
        DSL.noCondition()
    }

/**
 * Создать условие поиска значения типа LocalDate в диапазоне значений полей
 *
 * @param date дата для сравнения
 * @param from поле с
 * @param to поле по
 * @return Условие
 */
fun between(date: LocalDate?, from: Field<LocalDate?>, to: Field<LocalDate?>) = date?.let { from.le(it).and(to.ge(it)) } ?: DSL.noCondition()

/**
 * Создать условие поиска значения типа LocalDate вне диапазона значений полей
 *
 * @param date дата для сравнения
 * @param from поле с
 * @param to поле по
 * @return Условие
 */
fun notBetween(date: LocalDate?, from: Field<LocalDate?>, to: Field<LocalDate?>) = date?.let { from.gt(it).or(to.lt(it)) } ?: DSL.noCondition()

/**
 * Создать условие поиска значения типа LocalDateTime в диапазоне значений полей
 *
 * @param dateTime дата и время для сравнения
 * @param from поле с
 * @param to поле по
 * @return Условие
 */
fun between(dateTime: LocalDateTime?, from: Field<LocalDateTime?>, to: Field<LocalDateTime?>) =
    dateTime?.let { from.le(it).and(to.ge(it)) } ?: DSL.noCondition()

/**
 * Создать условие поиска значения поля в диапазоне значений типа LocalDateTime
 *
 * @param field дата и время для сравнения
 * @param from поле с
 * @param to поле по
 * @return Условие
 */
fun between(field: Field<LocalDateTime?>, from: LocalDateTime?, to: LocalDateTime?) =
    if (from != null && to != null) {
        field.le(to).and(field.ge(from))
    } else {
        DSL.noCondition()
    }

/**
 * Создать условие поиска значения поля в диапазоне значений типа OffsetDateTime
 *
 * @param field поле для сравнения
 * @param from дата и время с
 * @param to дата и время по
 * @return Условие
 */
fun between(field: Field<OffsetDateTime?>, from: OffsetDateTime?, to: OffsetDateTime?) =
    if (from != null && to != null) {
        field.le(from).and(field.ge(to))
    } else {
        DSL.noCondition()
    }

/**
 * Добавить условие, что указанный [field] больше или равен указанному [value]. Если значение, с которым сравнивается
 * поле, равно `null`, то условие не добавляется.
 *
 * @param field поле
 * @param value значение
 * @param T тип значения
 */
fun <T> goe(field: Field<T?>, value: T?): Condition =
    if (value != null) {
        field.greaterOrEqual(value)
    } else {
        DSL.noCondition()
    }

/**
 * Добавить условие, что указанный [field] меньше или равен указанному [value]. Если значение, с которым сравнивается
 * поле, равно `null`, то условие не добавляется.
 *
 * @param field поле
 * @param value значение
 * @param T тип значения
 */
fun <T> loe(field: Field<T?>, value: T?): Condition =
    if (value != null) {
        field.lessOrEqual(value)
    } else {
        DSL.noCondition()
    }

/*
============================================================
  Вспомогательные методы для построения комбинации условий
============================================================
 */
/**
 * Создать комбинацию двух условий по OR.
 *
 * @param left Левое условие
 * @param right Правое условие
 * @return Результирующее условие
 */
fun or(left: Condition, right: Condition) = left.or(right)

/**
 * Создать комбинацию коллекции условий по OR.
 *
 * @param conditions Коллекций условий
 * @return Результирующее условие
 */
fun or(conditions: Collection<Condition>) =
    if (conditions.isNotEmpty()) {
        DSL.or(conditions)
    } else {
        DSL.noCondition()
    }

/**
 * Создать комбинацию набора условий по OR.
 *
 * @param conditions Набор условий
 * @return Результирующее условие
 */
fun or(vararg conditions: Condition) =
    if (conditions.isNotEmpty()) {
        DSL.or(*conditions)
    } else {
        DSL.noCondition()
    }

/**
 * Создать комбинацию двух условий по AND.
 *
 * @param left Левое условие
 * @param right Правое условие
 * @return Результирующее условие
 */
fun and(left: Condition, right: Condition) = left.and(right)

/**
 * Создать комбинацию коллекции условий по AND.
 *
 * @param conditions Коллекций условий
 * @return Результирующее условие
 */
fun and(conditions: Collection<Condition>) =
    if (conditions.isNotEmpty()) {
        DSL.and(conditions)
    } else {
        DSL.noCondition()
    }

/**
 * Создать комбинацию набора условий по AND.
 *
 * @param conditions Набор условий
 * @return Результирующее условие
 */
fun and(vararg conditions: Condition) =
    if (conditions.isNotEmpty()) {
        DSL.and(*conditions)
    } else {
        DSL.noCondition()
    }
