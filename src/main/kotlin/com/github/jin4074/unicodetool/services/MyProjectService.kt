package com.github.jin4074.unicodetool.services

import com.intellij.openapi.project.Project
import com.github.jin4074.unicodetool.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
