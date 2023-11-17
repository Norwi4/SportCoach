package com.techsolutions.sportcoach

import com.techsolutions.sportcoach.jooq.domain.techsolutions_sportcoach.Tables.STAFF
import org.jooq.DSLContext

/**
 *
 * @author Abaev Evgeniy
 */
internal class test(
    private val dsl: DSLContext
) {
    fun getList() {
        var a = dsl.select(STAFF.ID)
            .from(STAFF)

    }

}