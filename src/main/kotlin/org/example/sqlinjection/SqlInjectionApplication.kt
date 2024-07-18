package org.example.sqlinjection

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SqlInjectionApplication

fun main(args: Array<String>) {
	runApplication<SqlInjectionApplication>(*args)
}
