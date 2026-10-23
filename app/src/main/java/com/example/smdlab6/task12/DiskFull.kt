package com.example.smdlab6.task12

class DiskFull(val drive: String, val freeMb: Long) : IoError() {
    override fun getReport() = "Disk Full on drive $drive (${freeMb}MB remaining)"
}
