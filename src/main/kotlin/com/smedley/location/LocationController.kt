package com.smedley.location

import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
class LocationController {
    val data: HashMap<Int, List<String>> = HashMap<Int, List<String>>()

    data class Location(val location: Int, val zipCodes: List<String>)
    data class ValidLocations(val existingLocations: List<Int>)

    @GetMapping(value = ["/location"])
    fun getLocation(): ValidLocations {
        return ValidLocations(data.keys.toList())
    }

    @GetMapping(value = ["/location/{id}"])
    fun getById(@PathVariable id: Int): Location {
        if (data[id] != null) return Location(id, data[id]!!)
        else throw Exception("Invalid location: $id")
    }

    @PutMapping(value = ["/location/{id}"])
    fun updateLocation(@PathVariable id: Int, @RequestBody loc: Location) {
        if (data.containsKey(id)) data[id] = loc.zipCodes
        else throw Exception("Invalid location: $id")
    }

    @PostMapping(value = ["/location/{id}"])
    fun createLocation(@PathVariable id: Int, @RequestBody loc: Location) {
        if (id <= 0) throw Exception("Must specify positive integer for location. Use JSON format { \"location\": 1, \"zipCodes\": [] }")
        if (data.containsKey(id)) throw Exception("Location $id already exists. Use PUT to modify or DELETE to remove instead.")
        data[id] = loc.zipCodes
    }

    @DeleteMapping(value = ["/location/{id}"])
    fun deleteLocation(@PathVariable id: Int) {
        data.remove(id)
    }
}