Type Difficulty
	Field Name$
	Field Description$
	Field AggressiveNPCs%
	Field SaveType%
	Field OtherFactors%
	Field Customizable%
	Field R%, G%, B%
	Field InventorySlots%
End Type

Global DifficultyDMGMult#
Global difficulties.Difficulty[5]

Global SelectedDifficulty.Difficulty

; ~ Difficulties ID Constants
;[Block]
Const SAFE% = 0
Const EUCLID% = 1
Const KETER% = 2
Const APOLLYON% = 3
Const ESOTERIC% = 4
;[End Block]

; ~ Save Types ID Constants
;[Block]
Const SAVE_ANYWHERE% = 0
Const SAVE_ON_SCREENS% = 1
Const SAVE_ON_QUIT% = 2
Const NO_SAVES% = 3
;[End Block]

; ~ Other Factors ID Constants
;[Block]
Const DIFFICULTY_EASY% = 0
Const DIFFICULTY_NORMAL% = 1
Const DIFFICULTY_HARD% = 2
Const DIFFICULTY_EXTREME% = 3
;[End Block]

Function SetDifficultyColor%(ID%, R%, G%, B%)
	difficulties[ID]\R = R
	difficulties[ID]\G = G
	difficulties[ID]\B = B
End Function

; ~ Configure difficulties
;[Block]
difficulties[SAFE] = New Difficulty
difficulties[SAFE]\Name = GetLocalString("menu", "new.safe")
difficulties[SAFE]\Description = GetLocalString("msg", "diff.safe")
difficulties[SAFE]\AggressiveNPCs = False
difficulties[SAFE]\InventorySlots = 10
difficulties[SAFE]\SaveType = SAVE_ANYWHERE
difficulties[SAFE]\OtherFactors = DIFFICULTY_EASY
SetDifficultyColor(SAFE, 120, 150, 50)

difficulties[EUCLID] = New Difficulty
difficulties[EUCLID]\Name = GetLocalString("menu", "new.euclid")
difficulties[EUCLID]\Description = GetLocalString("msg", "diff.euclid")
difficulties[EUCLID]\AggressiveNPCs = False
difficulties[EUCLID]\InventorySlots = 8
difficulties[EUCLID]\SaveType = SAVE_ON_SCREENS
difficulties[EUCLID]\OtherFactors = DIFFICULTY_NORMAL
SetDifficultyColor(EUCLID, 200, 200, 50)

difficulties[KETER] = New Difficulty
difficulties[KETER]\Name = GetLocalString("menu", "new.keter")
difficulties[KETER]\Description = GetLocalString("msg", "diff.keter")
difficulties[KETER]\AggressiveNPCs = True
difficulties[KETER]\InventorySlots = 6
difficulties[KETER]\SaveType = SAVE_ON_QUIT
difficulties[KETER]\OtherFactors = DIFFICULTY_HARD
SetDifficultyColor(KETER, 200, 50, 50)

difficulties[APOLLYON] = New Difficulty
difficulties[APOLLYON]\Name = GetLocalString("menu", "new.apollyon")
difficulties[APOLLYON]\Description = GetLocalString("msg", "diff.apollyon")
difficulties[APOLLYON]\AggressiveNPCs = True
difficulties[APOLLYON]\InventorySlots = 2
difficulties[APOLLYON]\SaveType = NO_SAVES
difficulties[APOLLYON]\OtherFactors = DIFFICULTY_EXTREME
SetDifficultyColor(APOLLYON, 150, 150, 150)

difficulties[ESOTERIC] = New Difficulty
difficulties[ESOTERIC]\Name = GetLocalString("menu", "new.esoteric")
difficulties[ESOTERIC]\AggressiveNPCs = False
difficulties[ESOTERIC]\InventorySlots = 10
difficulties[ESOTERIC]\Customizable = True
difficulties[ESOTERIC]\SaveType = SAVE_ANYWHERE
difficulties[ESOTERIC]\OtherFactors = DIFFICULTY_EASY
SetDifficultyColor(ESOTERIC, 200, 50, 200)
;[End Block]

SelectedDifficulty = difficulties[(Not opt\DebugMode)] ; ~ NOTICE: Const SAFE% = 0 and Const EUCLID% = 1

;~IDEal Editor Parameters:
;~C#Blitz3D TSS