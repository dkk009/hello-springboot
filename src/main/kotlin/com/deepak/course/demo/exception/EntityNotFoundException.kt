package com.deepak.course.demo.exception

import org.springframework.web.client.ResourceAccessException

class EntityNotFoundException(private val resourceId:Int): Exception("Entity not found:$resourceId") {

}