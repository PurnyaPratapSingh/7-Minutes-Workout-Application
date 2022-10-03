package com.purnya5151.a7minworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList =  ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(

            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )

        exerciseList.add(jumpingJacks)

        val wallSit = ExerciseModel(

            2,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false
        )

        exerciseList.add(wallSit)

        val crunches = ExerciseModel(

            3,
            "Abdominal Crunches",
            R.drawable.ic_abdominal_crunch,
            false,
            false
        )

        exerciseList.add(crunches)

        val highKnees = ExerciseModel(

            4,
            "High Knee Running in Place",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )

        exerciseList.add(highKnees)

        val lunge = ExerciseModel(

            5,
            "Lunges",
            R.drawable.ic_lunge,
            false,
            false
        )

        exerciseList.add(lunge)

        val plank = ExerciseModel(

            6,
            "Plank",
            R.drawable.ic_plank,
            false,
            false
        )

        exerciseList.add(plank)

        val pushUp = ExerciseModel(

            7,
            "Push-Up",
            R.drawable.ic_push_up,
            false,
            false
        )

        exerciseList.add(pushUp)

        val pushUpandRotation = ExerciseModel(

            8,
            "Push-Up and Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false
        )

        exerciseList.add(pushUpandRotation)

        val sidePlank = ExerciseModel(

            9,
            "Side Plank",
            R.drawable.ic_side_plank,
            false,
            false
        )

        exerciseList.add(sidePlank)

        val squat = ExerciseModel(

            10,
            "Squat",
            R.drawable.ic_squat,
            false,
            false
        )

        exerciseList.add(squat)

        val stepUpChair = ExerciseModel(

            11,
            "Step Up onto chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false
        )

        exerciseList.add(stepUpChair)

        val tricepsDip = ExerciseModel(

            12,
            "Triceps Dips on chair",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false
        )

        exerciseList.add(tricepsDip)
        return exerciseList
    }

}