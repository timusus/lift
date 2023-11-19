package com.simplecityapps.shuttle.common.network

object JsonData {
    val routines = """
        [
            {
                "id": 1,
                "name": "Lower Body (Strength)",
                "sort_order": 0,
                "synced": 0
            },
            {
                "id": 2,
                "name": "Upper Body (Strength)",
                "sort_order": 1,
                "synced": 0
            },
            {
                "id": 3,
                "name": "Lower Body (Volume)",
                "sort_order": 2,
                "synced": 0
            },
            {
                "id": 4,
                "name": "Upper Body (Volume)",
                "sort_order": 3,
                "synced": 0
            }
        ]
    """.trimIndent()
    val routineExercises = """
[
    {
        "exerciseId": 1,
        "id": 1,
        "percent_one_rep_max": 0.824999988079071,
        "reps": 5,
        "routineId": 1,
        "sets": 3,
        "sort_order": 0,
        "synced": 0,
        "weight": 60.0
    },
    {
        "exerciseId": 2,
        "id": 2,
        "percent_one_rep_max": 0.824999988079071,
        "reps": 5,
        "routineId": 1,
        "sets": 3,
        "sort_order": 1,
        "synced": 0,
        "weight": 100.0
    },
    {
        "exerciseId": 3,
        "id": 3,
        "percent_one_rep_max": null,
        "reps": 8,
        "routineId": 1,
        "sets": 3,
        "sort_order": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "exerciseId": 4,
        "id": 4,
        "percent_one_rep_max": null,
        "reps": 8,
        "routineId": 1,
        "sets": 4,
        "sort_order": 3,
        "synced": 0,
        "weight": 20.0
    },
    {
        "exerciseId": 5,
        "id": 5,
        "percent_one_rep_max": 0.824999988079071,
        "reps": 5,
        "routineId": 2,
        "sets": 3,
        "sort_order": 0,
        "synced": 0,
        "weight": 80.0
    },
    {
        "exerciseId": 6,
        "id": 6,
        "percent_one_rep_max": null,
        "reps": 5,
        "routineId": 2,
        "sets": 3,
        "sort_order": 1,
        "synced": 0,
        "weight": 70.0
    },
    {
        "exerciseId": 7,
        "id": 7,
        "percent_one_rep_max": 0.7250000238418579,
        "reps": 8,
        "routineId": 2,
        "sets": 2,
        "sort_order": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "exerciseId": 8,
        "id": 8,
        "percent_one_rep_max": null,
        "reps": 8,
        "routineId": 2,
        "sets": 2,
        "sort_order": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "exerciseId": 9,
        "id": 9,
        "percent_one_rep_max": null,
        "reps": 15,
        "routineId": 2,
        "sets": 2,
        "sort_order": 4,
        "synced": 0,
        "weight": 15.0
    },
    {
        "exerciseId": 10,
        "id": 10,
        "percent_one_rep_max": 0.75,
        "reps": 8,
        "routineId": 3,
        "sets": 3,
        "sort_order": 0,
        "synced": 0,
        "weight": 50.0
    },
    {
        "exerciseId": 11,
        "id": 11,
        "percent_one_rep_max": null,
        "reps": 8,
        "routineId": 3,
        "sets": 3,
        "sort_order": 1,
        "synced": 0,
        "weight": 60.0
    },
    {
        "exerciseId": 12,
        "id": 12,
        "percent_one_rep_max": null,
        "reps": 12,
        "routineId": 3,
        "sets": 3,
        "sort_order": 2,
        "synced": 0,
        "weight": 40.0
    },
    {
        "exerciseId": 13,
        "id": 13,
        "percent_one_rep_max": null,
        "reps": 12,
        "routineId": 3,
        "sets": 3,
        "sort_order": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "exerciseId": 14,
        "id": 14,
        "percent_one_rep_max": null,
        "reps": 15,
        "routineId": 3,
        "sets": 4,
        "sort_order": 4,
        "synced": 0,
        "weight": 60.0
    },
    {
        "exerciseId": 5,
        "id": 15,
        "percent_one_rep_max": 0.675000011920929,
        "reps": 10,
        "routineId": 4,
        "sets": 3,
        "sort_order": 0,
        "synced": 0,
        "weight": 70.0
    },
    {
        "exerciseId": 6,
        "id": 16,
        "percent_one_rep_max": null,
        "reps": 10,
        "routineId": 4,
        "sets": 3,
        "sort_order": 1,
        "synced": 0,
        "weight": 60.0
    },
    {
        "exerciseId": 17,
        "id": 17,
        "percent_one_rep_max": null,
        "reps": 12,
        "routineId": 4,
        "sets": 2,
        "sort_order": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "exerciseId": 8,
        "id": 18,
        "percent_one_rep_max": null,
        "reps": 12,
        "routineId": 4,
        "sets": 2,
        "sort_order": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "exerciseId": 15,
        "id": 19,
        "percent_one_rep_max": null,
        "reps": 12,
        "routineId": 4,
        "sets": 2,
        "sort_order": 4,
        "synced": 0,
        "weight": 60.0
    },
    {
        "exerciseId": 16,
        "id": 20,
        "percent_one_rep_max": null,
        "reps": 12,
        "routineId": 4,
        "sets": 2,
        "sort_order": 5,
        "synced": 0,
        "weight": 20.0
    }
]
""".trimIndent()

    val sessionExercises = """
[
    {
        "currentSet": 0,
        "endDate": null,
        "id": 5,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 2,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 6,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 2,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 7,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 2,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 8,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 2,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 9,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 2,
        "sets": 2,
        "synced": 0,
        "weight": 5.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 10,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 3,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 11,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 3,
        "sets": 3,
        "synced": 0,
        "weight": 100.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 12,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 3,
        "sets": 3,
        "synced": 0,
        "weight": 10.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 13,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 3,
        "sets": 4,
        "synced": 0,
        "weight": 111.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 14,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 4,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 15,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 4,
        "sets": 3,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 16,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 4,
        "sets": 2,
        "synced": 0,
        "weight": 5.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 17,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 4,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 18,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 4,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 19,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 4,
        "sets": 2,
        "synced": 0,
        "weight": 10.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 20,
        "reps": 8,
        "routineExerciseId": 10,
        "sessionId": 5,
        "sets": 3,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 21,
        "reps": 8,
        "routineExerciseId": 11,
        "sessionId": 5,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 22,
        "reps": 12,
        "routineExerciseId": 12,
        "sessionId": 5,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 23,
        "reps": 12,
        "routineExerciseId": 13,
        "sessionId": 5,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 24,
        "reps": 15,
        "routineExerciseId": 14,
        "sessionId": 5,
        "sets": 4,
        "synced": 0,
        "weight": 30.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 25,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 6,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 26,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 6,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 27,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 6,
        "sets": 2,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 28,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 6,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 29,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 6,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 36,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 7,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 37,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 7,
        "sets": 3,
        "synced": 0,
        "weight": 100.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 38,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 7,
        "sets": 3,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 39,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 7,
        "sets": 4,
        "synced": 0,
        "weight": 111.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 40,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 8,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 41,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 8,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 42,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 8,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 43,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 8,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 44,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 8,
        "sets": 2,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 45,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 8,
        "sets": 2,
        "synced": 0,
        "weight": 10.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 46,
        "reps": 8,
        "routineExerciseId": 10,
        "sessionId": 9,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 47,
        "reps": 8,
        "routineExerciseId": 11,
        "sessionId": 9,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 48,
        "reps": 12,
        "routineExerciseId": 12,
        "sessionId": 9,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 49,
        "reps": 12,
        "routineExerciseId": 13,
        "sessionId": 9,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 50,
        "reps": 15,
        "routineExerciseId": 14,
        "sessionId": 9,
        "sets": 4,
        "synced": 0,
        "weight": 30.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 51,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 10,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 52,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 10,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 53,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 10,
        "sets": 2,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 54,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 10,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 55,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 10,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 56,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 11,
        "sets": 3,
        "synced": 0,
        "weight": 90.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 57,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 11,
        "sets": 3,
        "synced": 0,
        "weight": 120.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 58,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 11,
        "sets": 3,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 59,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 11,
        "sets": 4,
        "synced": 0,
        "weight": 111.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 60,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 12,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 61,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 12,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 62,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 12,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 63,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 12,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 64,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 12,
        "sets": 2,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 65,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 12,
        "sets": 2,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 66,
        "reps": 8,
        "routineExerciseId": 10,
        "sessionId": 13,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 67,
        "reps": 8,
        "routineExerciseId": 11,
        "sessionId": 13,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 68,
        "reps": 12,
        "routineExerciseId": 12,
        "sessionId": 13,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 69,
        "reps": 12,
        "routineExerciseId": 13,
        "sessionId": 13,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 70,
        "reps": 15,
        "routineExerciseId": 14,
        "sessionId": 13,
        "sets": 4,
        "synced": 0,
        "weight": 30.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 75,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 14,
        "sets": 3,
        "synced": 0,
        "weight": 65.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 76,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 14,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 77,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 14,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 78,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 14,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 79,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 14,
        "sets": 2,
        "synced": 0,
        "weight": 5.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 80,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 15,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 81,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 15,
        "sets": 3,
        "synced": 0,
        "weight": 100.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 82,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 15,
        "sets": 3,
        "synced": 0,
        "weight": 10.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 83,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 15,
        "sets": 4,
        "synced": 0,
        "weight": 111.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 84,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 16,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 85,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 16,
        "sets": 3,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 86,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 16,
        "sets": 2,
        "synced": 0,
        "weight": 5.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 87,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 16,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 88,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 16,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 89,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 16,
        "sets": 2,
        "synced": 0,
        "weight": 10.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 90,
        "reps": 8,
        "routineExerciseId": 10,
        "sessionId": 17,
        "sets": 3,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 91,
        "reps": 8,
        "routineExerciseId": 11,
        "sessionId": 17,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 92,
        "reps": 12,
        "routineExerciseId": 12,
        "sessionId": 17,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 93,
        "reps": 12,
        "routineExerciseId": 13,
        "sessionId": 17,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 94,
        "reps": 15,
        "routineExerciseId": 14,
        "sessionId": 17,
        "sets": 4,
        "synced": 0,
        "weight": 30.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 95,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 18,
        "sets": 3,
        "synced": 0,
        "weight": 65.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 96,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 18,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 97,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 18,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 98,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 18,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 99,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 18,
        "sets": 2,
        "synced": 0,
        "weight": 5.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 100,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 19,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 101,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 19,
        "sets": 3,
        "synced": 0,
        "weight": 100.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 102,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 19,
        "sets": 3,
        "synced": 0,
        "weight": 10.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 103,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 19,
        "sets": 4,
        "synced": 0,
        "weight": 111.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 104,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 20,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 105,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 20,
        "sets": 3,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 106,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 20,
        "sets": 2,
        "synced": 0,
        "weight": 5.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 107,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 20,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 108,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 20,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 109,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 20,
        "sets": 2,
        "synced": 0,
        "weight": 10.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 110,
        "reps": 8,
        "routineExerciseId": 10,
        "sessionId": 21,
        "sets": 3,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 111,
        "reps": 8,
        "routineExerciseId": 11,
        "sessionId": 21,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 112,
        "reps": 12,
        "routineExerciseId": 12,
        "sessionId": 21,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 113,
        "reps": 12,
        "routineExerciseId": 13,
        "sessionId": 21,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 114,
        "reps": 15,
        "routineExerciseId": 14,
        "sessionId": 21,
        "sets": 4,
        "synced": 0,
        "weight": 30.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 115,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 22,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 116,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 22,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 117,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 22,
        "sets": 2,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 118,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 22,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 119,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 22,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 120,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 23,
        "sets": 3,
        "synced": 0,
        "weight": 90.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 121,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 23,
        "sets": 3,
        "synced": 0,
        "weight": 130.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 122,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 23,
        "sets": 3,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 123,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 23,
        "sets": 4,
        "synced": 0,
        "weight": 111.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 124,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 24,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 125,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 24,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 126,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 24,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 127,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 24,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 128,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 24,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 129,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 24,
        "sets": 2,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 130,
        "reps": 8,
        "routineExerciseId": 10,
        "sessionId": 25,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 131,
        "reps": 8,
        "routineExerciseId": 11,
        "sessionId": 25,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 132,
        "reps": 12,
        "routineExerciseId": 12,
        "sessionId": 25,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 133,
        "reps": 12,
        "routineExerciseId": 13,
        "sessionId": 25,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 134,
        "reps": 15,
        "routineExerciseId": 14,
        "sessionId": 25,
        "sets": 4,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 135,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 26,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 136,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 26,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 137,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 26,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 138,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 26,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 139,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 26,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 140,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 27,
        "sets": 3,
        "synced": 0,
        "weight": 90.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 141,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 27,
        "sets": 3,
        "synced": 0,
        "weight": 130.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 142,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 27,
        "sets": 3,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 143,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 27,
        "sets": 4,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 144,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 28,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 145,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 28,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 146,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 28,
        "sets": 2,
        "synced": 0,
        "weight": null
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 147,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 28,
        "sets": 2,
        "synced": 0,
        "weight": null
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 148,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 28,
        "sets": 2,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 149,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 28,
        "sets": 2,
        "synced": 0,
        "weight": null
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 150,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 29,
        "sets": 3,
        "synced": 0,
        "weight": 85.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 151,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 29,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 152,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 29,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 153,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 29,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 154,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 29,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 155,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 30,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 156,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 30,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 157,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 30,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 158,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 30,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 159,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 30,
        "sets": 2,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 160,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 30,
        "sets": 2,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 161,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 31,
        "sets": 3,
        "synced": 0,
        "weight": 85.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 162,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 31,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 163,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 31,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 164,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 31,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 165,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 31,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 166,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 32,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 167,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 32,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 168,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 32,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 169,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 32,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 170,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 32,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 171,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 33,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 172,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 33,
        "sets": 3,
        "synced": 0,
        "weight": 100.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 173,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 33,
        "sets": 3,
        "synced": 0,
        "weight": 10.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 174,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 33,
        "sets": 4,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 175,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 34,
        "sets": 3,
        "synced": 0,
        "weight": 80.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 176,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 34,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 177,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 34,
        "sets": 2,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 178,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 34,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 179,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 34,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 180,
        "reps": 8,
        "routineExerciseId": 10,
        "sessionId": 35,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 181,
        "reps": 8,
        "routineExerciseId": 11,
        "sessionId": 35,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 182,
        "reps": 12,
        "routineExerciseId": 12,
        "sessionId": 35,
        "sets": 3,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 183,
        "reps": 12,
        "routineExerciseId": 13,
        "sessionId": 35,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 184,
        "reps": 15,
        "routineExerciseId": 14,
        "sessionId": 35,
        "sets": 4,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 185,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 36,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 186,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 36,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 187,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 36,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 188,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 36,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 189,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 36,
        "sets": 2,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 190,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 36,
        "sets": 2,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 191,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 37,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 192,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 37,
        "sets": 3,
        "synced": 0,
        "weight": 100.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 193,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 37,
        "sets": 3,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 194,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 37,
        "sets": 4,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 195,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 38,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 196,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 38,
        "sets": 3,
        "synced": 0,
        "weight": 100.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 197,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 38,
        "sets": 3,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 198,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 38,
        "sets": 4,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 199,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 39,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 200,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 39,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 201,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 39,
        "sets": 2,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 202,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 39,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 203,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 39,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 204,
        "reps": 8,
        "routineExerciseId": 10,
        "sessionId": 40,
        "sets": 3,
        "synced": 0,
        "weight": 10.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 205,
        "reps": 8,
        "routineExerciseId": 11,
        "sessionId": 40,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 206,
        "reps": 12,
        "routineExerciseId": 12,
        "sessionId": 40,
        "sets": 3,
        "synced": 0,
        "weight": 20.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 207,
        "reps": 12,
        "routineExerciseId": 13,
        "sessionId": 40,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 208,
        "reps": 15,
        "routineExerciseId": 14,
        "sessionId": 40,
        "sets": 4,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 209,
        "reps": 10,
        "routineExerciseId": 15,
        "sessionId": 41,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 210,
        "reps": 10,
        "routineExerciseId": 16,
        "sessionId": 41,
        "sets": 3,
        "synced": 0,
        "weight": 50.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 211,
        "reps": 12,
        "routineExerciseId": 17,
        "sessionId": 41,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 212,
        "reps": 12,
        "routineExerciseId": 18,
        "sessionId": 41,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 213,
        "reps": 12,
        "routineExerciseId": 19,
        "sessionId": 41,
        "sets": 2,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 214,
        "reps": 12,
        "routineExerciseId": 20,
        "sessionId": 41,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 215,
        "reps": 5,
        "routineExerciseId": 1,
        "sessionId": 42,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 216,
        "reps": 5,
        "routineExerciseId": 2,
        "sessionId": 42,
        "sets": 3,
        "synced": 0,
        "weight": 120.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 217,
        "reps": 8,
        "routineExerciseId": 3,
        "sessionId": 42,
        "sets": 3,
        "synced": 0,
        "weight": 15.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 218,
        "reps": 8,
        "routineExerciseId": 4,
        "sessionId": 42,
        "sets": 4,
        "synced": 0,
        "weight": 30.0
    },
    {
        "currentSet": 2,
        "endDate": "2023-10-30T01:48:30.565398Z",
        "id": 219,
        "reps": 5,
        "routineExerciseId": 5,
        "sessionId": 43,
        "sets": 3,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 2,
        "endDate": "2023-10-30T02:04:42.957106Z",
        "id": 220,
        "reps": 5,
        "routineExerciseId": 6,
        "sessionId": 43,
        "sets": 3,
        "synced": 0,
        "weight": 60.0
    },
    {
        "currentSet": 1,
        "endDate": "2023-10-30T02:10:39.776238Z",
        "id": 221,
        "reps": 8,
        "routineExerciseId": 7,
        "sessionId": 43,
        "sets": 2,
        "synced": 0,
        "weight": 40.0
    },
    {
        "currentSet": 1,
        "endDate": "2023-10-30T02:15:01.641705Z",
        "id": 222,
        "reps": 8,
        "routineExerciseId": 8,
        "sessionId": 43,
        "sets": 2,
        "synced": 0,
        "weight": 70.0
    },
    {
        "currentSet": 0,
        "endDate": null,
        "id": 223,
        "reps": 15,
        "routineExerciseId": 9,
        "sessionId": 43,
        "sets": 2,
        "synced": 0,
        "weight": 15.0
    }
]  
""".trimIndent()

    val sessions = """
[
    {
        "currentExerciseId": null,
        "endDate": "2021-03-17T04:31:15.455Z",
        "id": 2,
        "routineId": 2,
        "startDate": "2021-03-17T04:07:00.603Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-18T08:21:00.083Z",
        "id": 3,
        "routineId": 1,
        "startDate": "2021-03-18T07:40:00.759Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-19T04:23:25.616Z",
        "id": 4,
        "routineId": 4,
        "startDate": "2021-03-19T03:55:45.993Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-20T08:13:57.027Z",
        "id": 5,
        "routineId": 3,
        "startDate": "2021-03-20T08:12:51.016Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-22T06:40:56.032Z",
        "id": 6,
        "routineId": 2,
        "startDate": "2021-03-22T05:40:51.712Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-23T07:32:27.790Z",
        "id": 7,
        "routineId": 1,
        "startDate": "2021-03-23T06:57:27.973Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-24T06:48:33.545Z",
        "id": 8,
        "routineId": 4,
        "startDate": "2021-03-24T06:10:34.273Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-25T06:53:47.906Z",
        "id": 9,
        "routineId": 3,
        "startDate": "2021-03-25T05:47:35.620Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-27T07:47:27.828Z",
        "id": 10,
        "routineId": 2,
        "startDate": "2021-03-27T07:12:09.607Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-28T10:10:34.547Z",
        "id": 11,
        "routineId": 1,
        "startDate": "2021-03-28T09:27:48.191Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-29T03:36:06.825Z",
        "id": 12,
        "routineId": 4,
        "startDate": "2021-03-29T02:31:50.755Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-30T07:26:38.572Z",
        "id": 13,
        "routineId": 3,
        "startDate": "2021-03-30T06:42:24.677Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-12T04:31:15.455Z",
        "id": 14,
        "routineId": 2,
        "startDate": "2021-03-12T04:07:00.603Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-13T08:21:00.083Z",
        "id": 15,
        "routineId": 1,
        "startDate": "2021-03-13T08:20:00.759Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-14T04:23:25.616Z",
        "id": 16,
        "routineId": 4,
        "startDate": "2021-03-14T03:55:45.993Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-15T08:13:57.027Z",
        "id": 17,
        "routineId": 3,
        "startDate": "2021-03-15T07:30:51.016Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-07T06:40:56.032Z",
        "id": 18,
        "routineId": 2,
        "startDate": "2021-03-07T06:00:51.712Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-08T07:32:27.790Z",
        "id": 19,
        "routineId": 1,
        "startDate": "2021-03-08T06:57:27.973Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-09T06:48:33.545Z",
        "id": 20,
        "routineId": 4,
        "startDate": "2021-03-09T06:10:34.273Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-03-10T06:53:47.906Z",
        "id": 21,
        "routineId": 3,
        "startDate": "2021-03-10T05:47:35.620Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-01T05:25:52.126Z",
        "id": 22,
        "routineId": 2,
        "startDate": "2021-04-01T04:58:14.097Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-02T00:23:03.926Z",
        "id": 23,
        "routineId": 1,
        "startDate": "2021-04-01T23:36:29.907Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-06T09:28:55.693Z",
        "id": 24,
        "routineId": 4,
        "startDate": "2021-04-06T08:35:39.299Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-07T07:52:41.124Z",
        "id": 25,
        "routineId": 3,
        "startDate": "2021-04-07T07:10:20.535Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-09T09:24:17.945Z",
        "id": 26,
        "routineId": 2,
        "startDate": "2021-04-09T08:48:51.554Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-10T05:07:25.115Z",
        "id": 27,
        "routineId": 1,
        "startDate": "2021-04-10T04:01:50.137Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-15T09:23:03.330Z",
        "id": 28,
        "routineId": 4,
        "startDate": "2021-04-15T08:41:06.421Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-19T10:50:33.625Z",
        "id": 29,
        "routineId": 2,
        "startDate": "2021-04-19T10:18:40.564Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-21T12:05:08.008Z",
        "id": 30,
        "routineId": 4,
        "startDate": "2021-04-21T11:22:33.262Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-04-29T07:13:16.512Z",
        "id": 31,
        "routineId": 2,
        "startDate": "2021-04-29T06:45:25.103Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-05-25T12:45:56.990Z",
        "id": 32,
        "routineId": 2,
        "startDate": "2021-05-25T11:48:48.924Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-06-19T04:03:43.245Z",
        "id": 33,
        "routineId": 1,
        "startDate": "2021-06-19T03:41:06.537Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-06-22T04:29:17.309Z",
        "id": 34,
        "routineId": 2,
        "startDate": "2021-06-22T03:56:19.737Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-06-29T04:30:48.520Z",
        "id": 35,
        "routineId": 3,
        "startDate": "2021-06-29T03:42:34.392Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-07-07T05:23:10.184Z",
        "id": 36,
        "routineId": 4,
        "startDate": "2021-07-07T04:47:02.246Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2021-07-26T12:48:12.621Z",
        "id": 37,
        "routineId": 1,
        "startDate": "2021-07-26T12:16:58.293Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2023-09-28T06:22:07.572Z",
        "id": 38,
        "routineId": 1,
        "startDate": "2023-09-28T05:46:19.269Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2023-10-02T02:51:01.529Z",
        "id": 39,
        "routineId": 2,
        "startDate": "2023-10-02T01:59:20.667Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2023-10-05T02:54:34.285Z",
        "id": 40,
        "routineId": 3,
        "startDate": "2023-10-05T02:10:41.769Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2023-10-06T03:30:06.117371Z",
        "id": 41,
        "routineId": 4,
        "startDate": "2023-10-06T02:50:15.257972Z",
        "synced": 0
    },
    {
        "currentExerciseId": null,
        "endDate": "2023-10-09T01:53:15.556562Z",
        "id": 42,
        "routineId": 1,
        "startDate": "2023-10-09T01:17:48.119057Z",
        "synced": 0
    },
    {
        "currentExerciseId": 223,
        "endDate": null,
        "id": 43,
        "routineId": 2,
        "startDate": "2023-10-30T01:37:21.357001Z",
        "synced": 0
    }
]
""".trimIndent()


    val exercises = """
[
    {
        "id": 1,
        "name": "Squat",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 2,
        "name": "Deadlift",
        "one_rep_max": 150.0,
        "synced": 0
    },
    {
        "id": 3,
        "name": "Single leg",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 4,
        "name": "Standing calf-raises",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 5,
        "name": "Bench Press",
        "one_rep_max": 90.0,
        "synced": 0
    },
    {
        "id": 6,
        "name": "Horizontal pull",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 7,
        "name": "Shoulder Press",
        "one_rep_max": 70.0,
        "synced": 0
    },
    {
        "id": 8,
        "name": "Vertical pull",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 9,
        "name": "Flys",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 10,
        "name": "Hip thrust",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 11,
        "name": "Hack squat",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 12,
        "name": "Cursing lunges",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 13,
        "name": "Stiff-legged Deadlift",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 14,
        "name": "Seated calf-raise",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 15,
        "name": "Incline Bench Press",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 16,
        "name": "Triceps isolation",
        "one_rep_max": null,
        "synced": 0
    },
    {
        "id": 17,
        "name": "Biceps isolation",
        "one_rep_max": null,
        "synced": 0
    }
]  
""".trimIndent()
}