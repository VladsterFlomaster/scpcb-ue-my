Const SavePath$ = "Saves\"

Function SaveGame%(File$)
	If me\Zombie Lor me\Terminated Then Return ; ~ Don't save if the player is not alive
	
	If me\DropSpeed > 0.02 * fps\Factor[0] Lor me\DropSpeed < (-0.02) * fps\Factor[0] Then Return
	
	CatchErrors("SaveGame(" + File + ")")
	
	Local n.NPCs, r.Rooms, d.Doors, emit.Emitter
	Local x%, y%, i%, Temp%
	
	GameSaved = True
	
	File = SavePath + File
	
	CreateDir(File)
	
	Local f% = WriteFile(File + "\save.cb")
	
	WriteString(f, CurrentTime())
	WriteString(f, CurrentDate())
	
	WriteString(f, VersionNumber)
	
	If SelectedCustomMap = Null
		WriteByte(f, 0)
		WriteString(f, RandomSeed)
	Else
		WriteByte(f, 1)
		WriteString(f, SelectedCustomMap\Name)
	EndIf
	WriteString(f, SelectedDifficulty\Name)
	
	WriteInt(f, CODE_DR_MAYNARD)
	WriteInt(f, CODE_O5_COUNCIL)
	WriteInt(f, CODE_MAINTENANCE_TUNNELS)
	WriteInt(f, CODE_DR_GEARS)
	
	WriteFloat(f, EntityX(me\Collider))
	WriteFloat(f, EntityY(me\Collider))
	WriteFloat(f, EntityZ(me\Collider))
	
	WriteFloat(f, EntityX(me\Head))
	WriteFloat(f, EntityY(me\Head))
	WriteFloat(f, EntityZ(me\Head))
	
	WriteFloat(f, EntityPitch(me\Collider))
	WriteFloat(f, EntityYaw(me\Collider))
	
	WriteFloat(f, me\BlinkTimer)
	WriteFloat(f, me\BLINKFREQ)
	WriteFloat(f, me\BlinkEffect)
	WriteFloat(f, me\BlinkEffectTimer)
	
	WriteFloat(f, me\BlurTimer)
	WriteFloat(f, me\HealTimer)
	
	WriteByte(f, me\Crouch)
	
	WriteFloat(f, me\Stamina)
	WriteFloat(f, me\StaminaEffect)
	WriteFloat(f, me\StaminaEffectTimer)
	
	WriteFloat(f, me\EyeStuck)
	WriteFloat(f, me\EyeIrritation)
	
	WriteFloat(f, me\Injuries)
	WriteFloat(f, me\Bloodloss)
	
	WriteFloat(f, me\PrevInjuries)
	WriteFloat(f, me\PrevBloodloss)
	
	WriteString(f, msg\DeathMsg)
	
	WriteInt(f, me\CurrFunds)
	WriteByte(f, me\UsedMastercard)
	
	WriteFloat(f, me\VomitTimer)
	WriteByte(f, me\Vomit)
	WriteFloat(f, me\CameraShakeTimer)
	
	WriteFloat(f, me\CameraShake)
	WriteFloat(f, me\BigCameraShake)
	
	WriteFloat(f, me\LightBlink)
	WriteFloat(f, me\LightFlash)
	
	WriteFloat(f, me\Sanity)
	
	WriteInt(f, me\RefinedItems)
	
	WriteFloat(f, me\Pill2022Used)
	
	WriteFloat(f, me\Pill2022HealTimer)
	
	WriteByte(f, I_005\ChanceToSpawn)
	
	For i = 0 To 6
		WriteFloat(f, I_1025\State[i])
	Next
	For i = 0 To 4
		WriteFloat(f, I_1025\FineState[i])
	Next
	
	WriteFloat(f, I_008\Timer)
	WriteByte(f, I_008\Revert)
	WriteFloat(f, I_409\Timer)
	WriteByte(f, I_409\Revert)
	
	WriteByte(f, I_035\Sad)
	
	For i = SAFE To ESOTERIC
		If SelectedDifficulty = difficulties[i]
			WriteByte(f, i)
			
			If i = ESOTERIC
				WriteByte(f, SelectedDifficulty\AggressiveNPCs)
				WriteByte(f, SelectedDifficulty\SaveType)
				WriteByte(f, SelectedDifficulty\OtherFactors)
				WriteByte(f, SelectedDifficulty\InventorySlots)
			EndIf
		EndIf
	Next
	
	WriteFloat(f, wi\GasMaskFogTimer)
	
	WriteByte(f, wi\GasMask)
	WriteByte(f, wi\BallisticVest)
	WriteByte(f, wi\BallisticHelmet)
	WriteByte(f, wi\HazmatSuit)
	WriteByte(f, wi\NightVision)
	WriteByte(f, wi\SCRAMBLE)
	
	WriteByte(f, I_1499\Using)
	WriteFloat(f, I_1499\PrevX)
	WriteFloat(f, I_1499\PrevY)
	WriteFloat(f, I_1499\PrevZ)
	WriteFloat(f, I_1499\x)
	WriteFloat(f, I_1499\y)
	WriteFloat(f, I_1499\z)
	If I_1499\PrevRoom <> Null
		WriteFloat(f, I_1499\PrevRoom\x)
		WriteFloat(f, I_1499\PrevRoom\z)
	Else
		WriteFloat(f, 0.0)
		WriteFloat(f, 0.0)
	EndIf
	
	WriteFloat(f, I_966\HasInsomnia)
	WriteFloat(f, I_966\InsomniaEffectTimer)
	
	WriteFloat(f, I_1048A\EarGrowTimer)
	WriteByte(f, I_1048A\Revert)
	
	WriteByte(f, I_268\Using)
	WriteFloat(f, I_268\Timer)
	WriteFloat(f, I_268\PauseCharge)
	WriteByte(f, I_427\Using)
	WriteFloat(f, I_427\Timer)
	WriteByte(f, I_714\Using)
	WriteByte(f, I_294\Using)
	
	WriteString(f, RandomSeed)
	
	WriteFloat(f, SecondaryLightOn)
	WriteFloat(f, LightVolume)
	WriteByte(f, IsBlackOut)
	WriteByte(f, PrevIsBlackOut)
	
	WriteByte(f, RemoteDoorOn)
	
	WriteByte(f, SoundTransmission)
	
	WriteByte(f, KEY2_SPAWNRATE)
	
	Local Achievements% = JsonGetArray(JsonGetValue(AchievementsArray, "achievements"))
	Local ArraySize% = JsonGetArraySize(Achievements)
	
	For i = 0 To ArraySize - 1
		Local ID$ = JsonGetString(JsonGetValue(JsonGetArrayValue(Achievements, i), "id"))
		
		If S2IMapContains(UnlockedAchievements, ID) Then WriteString(f, ID)
	Next
	WriteString(f, "EOA") ; ~ End of achievements
	
	WriteByte(f, UsedConsole)
	
	WriteFloat(f, MTFTimer)
	
	WriteFloat(f, Remove714Timer)
	WriteFloat(f, RemoveHazmatTimer)
	
	For x = 0 To MapGridSize
		For y = 0 To MapGridSize
			WriteByte(f, CurrMapGrid\Grid[x + (y * MapGridSize)])
			WriteByte(f, CurrMapGrid\Found[x + (y * MapGridSize)])
		Next
	Next
	
	WriteInt(f, 113)
	
	Temp = 0
	For n.NPCs = Each NPCs
		Temp = Temp + 1
	Next
	
	WriteInt(f, Temp)
	For n.NPCs = Each NPCs
		WriteByte(f, n\NPCType)
		WriteFloat(f, EntityX(n\Collider, True))
		WriteFloat(f, EntityY(n\Collider, True))
		WriteFloat(f, EntityZ(n\Collider, True))
		
		WriteFloat(f, EntityPitch(n\Collider))
		WriteFloat(f, EntityYaw(n\Collider))
		WriteFloat(f, EntityRoll(n\Collider))
		
		WriteFloat(f, n\State)
		WriteFloat(f, n\State2)
		WriteFloat(f, n\State3)
		WriteInt(f, n\PrevState)
		
		WriteByte(f, n\Idle)
		WriteFloat(f, n\LastDist)
		WriteInt(f, n\LastSeen)
		
		WriteFloat(f, n\CurrSpeed)
		
		WriteFloat(f, n\Angle)
		
		WriteFloat(f, n\Reload)
		
		WriteInt(f, n\ID)
		If n\Target <> Null
			WriteInt(f, n\Target\ID)
		Else
			WriteInt(f, 0)
		EndIf
		
		WriteFloat(f, n\EnemyX)
		WriteFloat(f, n\EnemyY)
		WriteFloat(f, n\EnemyZ)
		
		WriteString(f, n\Texture)
		
		WriteByte(f, n\HasAsset)
		WriteByte(f, n\HasAnim)
		
		If n\HasAnim Then WriteFloat(f, AnimTime(n\OBJ))
		
		WriteByte(f, n\Contained)
		WriteByte(f, n\IsDead)
		WriteInt(f, n\HP)
		WriteFloat(f, n\ModelScale)
		WriteByte(f, n\TextureID)
		WriteByte(f, n\HideFromNVG)
		If n\NPCType = NPCTypeMTF Then WriteByte(f, (2 * (n_I\MTFLeader = n)) + (n_I\MTFCoLeader = n))
	Next
	
	WriteInt(f, 632)
	
	WriteByte(f, I_Zone\Transition[0])
	WriteByte(f, I_Zone\Transition[1])
	WriteByte(f, I_Zone\HasCustomForest)
	WriteByte(f, I_Zone\HasCustomMT)
	
	Temp = 0
	For r.Rooms = Each Rooms
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For r.Rooms = Each Rooms
		WriteInt(f, r\RoomTemplate\ID)
		WriteInt(f, r\Angle)
		WriteFloat(f, r\x)
		WriteFloat(f, r\y)
		WriteFloat(f, r\z)
		
		WriteByte(f, r\Found)
		WriteInt(f, r\Zone)
		
		WriteByte(f, PlayerRoom = r)
		
		For i = 0 To MaxRoomNPCs - 1
			If r\NPC[i] = Null
				WriteInt(f, 0)
			Else
				WriteInt(f, r\NPC[i]\ID)
			EndIf
		Next
		
		For i = 0 To MaxRoomLevers - 1
			If r\RoomLevers[i] = Null
				WriteByte(f, 2)
			Else
				WriteByte(f, EntityPitch(r\RoomLevers[i]\OBJ, True) =< 0.0)
			EndIf
		Next
		
		If r\mt = Null ; ~ This room doesn't have a grid
			WriteByte(f, 0)
		Else ; ~ This room has a grid
			WriteByte(f, 1)
			For y = 0 To MTGridSize - 1
				For x = 0 To MTGridSize - 1
					WriteByte(f, r\mt\Grid[x + (y * MTGridSize)])
					WriteByte(f, r\mt\Angles[x + (y * MTGridSize)])
				Next
			Next
		EndIf
		
		If r\fr = Null ; ~ This room doesn't have a forest
			WriteByte(f, 0)
		Else ; ~ This room has a forest
			If I_Zone\HasCustomForest
				WriteByte(f, 2)
			Else
				WriteByte(f, 1)
			EndIf
			For y = 0 To ForestGridSize - 1
				For x = 0 To ForestGridSize - 1
					WriteByte(f, r\fr\Grid[x + (y * ForestGridSize)])
				Next
			Next
			WriteFloat(f, EntityX(r\fr\Forest_Pivot, True))
			WriteFloat(f, EntityY(r\fr\Forest_Pivot, True))
			WriteFloat(f, EntityZ(r\fr\Forest_Pivot, True))
		EndIf
	Next
	
	WriteInt(f, 954)
	
	Temp = 0
	For emit.Emitter = Each Emitter
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For emit.Emitter = Each Emitter
		WriteFloat(f, EntityX(emit\Owner, True))
		WriteFloat(f, EntityY(emit\Owner, True))
		WriteFloat(f, EntityZ(emit\Owner, True))
		
		WriteInt(f, emit\ParticleID)
		WriteByte(f, emit\State)
		
		WriteInt(f, emit\EmitterID)
	Next
	
	For r.Rooms = Each Rooms
		For i = 0 To MaxRoomEmitters - 1
			If r\RoomEmitters[i] = Null
				WriteInt(f, 0)
			Else
				WriteInt(f, r\RoomEmitters[i]\EmitterID)
			EndIf
		Next
	Next
	
	WriteByte(f, bk\IsBroken)
	WriteFloat(f, bk\x)
	WriteFloat(f, bk\z)
	
	Temp = 0
	For d.Doors = Each Doors
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For d.Doors = Each Doors
		WriteFloat(f, EntityX(d\FrameOBJ, True))
		WriteFloat(f, EntityY(d\FrameOBJ, True))
		WriteFloat(f, EntityZ(d\FrameOBJ, True))
		WriteByte(f, d\Open)
		WriteFloat(f, d\OpenState)
		WriteByte(f, d\Locked)
		WriteByte(f, d\AutoClose)
		
		WriteFloat(f, EntityX(d\OBJ, True))
		WriteFloat(f, EntityZ(d\OBJ, True))
		WriteFloat(f, EntityYaw(d\OBJ, True))
		
		If d\OBJ2 <> 0
			WriteFloat(f, EntityX(d\OBJ2, True))
			WriteFloat(f, EntityZ(d\OBJ2, True))
		Else
			WriteFloat(f, 0.0)
			WriteFloat(f, 0.0)
		EndIf
		
		WriteFloat(f, d\Timer)
		WriteFloat(f, d\TimerState)
		
		WriteByte(f, d\IsElevatorDoor)
		WriteByte(f, d\MTFClose)
		
		WriteByte(f, d\IsAffected)
	Next
	
	WriteInt(f, 1845)
	
	Local de.Decals
	
	Temp = 0
	For de.Decals = Each Decals
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For de.Decals = Each Decals
		WriteInt(f, de\ID)
		
		WriteFloat(f, EntityX(de\OBJ, True))
		WriteFloat(f, EntityY(de\OBJ, True))
		WriteFloat(f, EntityZ(de\OBJ, True))
		
		WriteFloat(f, EntityPitch(de\OBJ, True))
		WriteFloat(f, EntityYaw(de\OBJ, True))
		WriteFloat(f, EntityRoll(de\OBJ, True))
		
		WriteFloat(f, de\Size)
		WriteFloat(f, de\MaxSize)
		WriteFloat(f, de\Alpha)
		WriteByte(f, de\FX)
		WriteByte(f, de\BlendMode)
		WriteByte(f, de\R) : WriteByte(f, de\G) : WriteByte(f, de\B)
		
		WriteFloat(f, de\Timer)
		WriteFloat(f, de\LifeTime)
		WriteFloat(f, de\SizeChange)
		WriteFloat(f, de\AlphaChange)
	Next
	
	Local e.Events
	
	Temp = 0
	For e.Events = Each Events
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For e.Events = Each Events
		WriteByte(f, e\EventID)
		WriteFloat(f, e\EventState)
		WriteFloat(f, e\EventState2)
		WriteFloat(f, e\EventState3)
		WriteFloat(f, e\EventState4)
		WriteFloat(f, EntityX(e\room\OBJ))
		WriteFloat(f, EntityZ(e\room\OBJ))
		WriteString(f, e\EventStr)
	Next
	
	Local it.Items
	
	Temp = 0
	For it.Items = Each Items
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For it.Items = Each Items
		WriteString(f, it\ItemTemplate\Name)
		WriteInt(f, it\ItemTemplate\ID)
		
		WriteString(f, it\Name)
		WriteString(f, it\DisplayName)
		
		WriteFloat(f, EntityX(it\Collider, True))
		WriteFloat(f, EntityY(it\Collider, True))
		WriteFloat(f, EntityZ(it\Collider, True))
		
		WriteByte(f, it\R)
		WriteByte(f, it\G)
		WriteByte(f, it\B)
		WriteFloat(f, it\Alpha)
		
		WriteFloat(f, EntityPitch(it\Collider))
		WriteFloat(f, EntityYaw(it\Collider))
		
		WriteFloat(f, it\State)
		WriteFloat(f, it\State2)
		WriteFloat(f, it\State3)
		WriteByte(f, it\Picked)
		
		WriteByte(f, SelectedItem = it)
		
		Local ItemFound% = False
		
		For i = 0 To MaxItemAmount - 1
			If Inventory(i) = it
				ItemFound = True
				Exit
			EndIf
		Next
		If ItemFound
			WriteByte(f, i)
		Else
			WriteByte(f, 66)
		EndIf
		
		If it\ItemTemplate\IsAnim Then WriteFloat(f, AnimTime(it\OBJ))
		WriteByte(f, it\InvSlots)
		WriteInt(f, it\ID)
		WriteByte(f, it\ItemTemplate\InvImg <> it\InvImg)
	Next
	
	Temp = 0
	For it.Items = Each Items
		If it\InvSlots > 0 Then Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For it.Items = Each Items
		If it\InvSlots > 0
			WriteInt(f, it\ID)
			For i = 0 To it\InvSlots - 1
				If it\SecondInv[i] <> Null
					WriteInt(f, it\SecondInv[i]\ID)
				Else
					WriteInt(f, -1)
				EndIf
			Next
		EndIf
	Next
	
	For it.Items = Each Items
		If it\ItemTemplate\ID = it_e_reader Lor it\ItemTemplate\ID = it_e_reader20 Lor it\ItemTemplate\ID = it_e_readerulti
			WriteByte(f, it\EReaderPageAmount)
			For i = 1 To it\EReaderPageAmount
				WriteString(f, it\EReaderPage[i]\Name)
			Next
		EndIf
	Next
	
	Local itt.ItemTemplates
	
	For itt.ItemTemplates = Each ItemTemplates
		WriteByte(f, itt\Found)
	Next
	
	For emit.Emitter = Each Emitter
		For r.Rooms = Each Rooms
			WriteByte(f, emit\room = r)
		Next
	Next
	
	WriteInt(f, EscapeTimer)
	
	Local sc.SecurityCams
	
	For sc.SecurityCams = Each SecurityCams
		WriteByte(f, sc\CoffinEffect)
		WriteInt(f, sc\PlayerState)
	Next
	
	CloseFile(f)
	
	If SelectedDifficulty\SaveType = SAVE_ON_SCREENS
		PlaySound_Strict(LoadTempSound("SFX\General\Save1.ogg"))
	Else
		PlaySound_Strict(LoadTempSound("SFX\General\Save0.ogg"))
		as\Timer = 70.0 * 70.0
	EndIf
	CreateHintMsg(GetLocalString("save", "saved"))
	
	CatchErrors("Uncaught: SaveGame(" + File + ")")
End Function

Function LoadGame%(File$)
	CatchErrors("LoadGame(" + File + ")")
	
	Local r.Rooms, n.NPCs, d.Doors, emit.Emitter, rt.RoomTemplates
	Local x#, y#, z#, i%, j%, Temp% = 0, Temp2% = 0, StrTemp$ = "", Tex%, ID%
	Local f% = ReadFile_Strict(SavePath + File + "\save.cb")
	
	me\DropSpeed = 0.0
	
	GameSaved = True
	
	ReadString(f)
	ReadString(f)
	
	StrTemp = ReadString(f)
	If StrTemp <> VersionNumber Then RuntimeErrorEx(Format(Format(GetLocalString("save", "imcompatible"), StrTemp, "{0}"), VersionNumber, "{1}"))
	
	ReadByte(f)
	ReadString(f)
	ReadString(f)
	
	CODE_DR_MAYNARD = ReadInt(f)
	CODE_O5_COUNCIL = ReadInt(f)
	CODE_MAINTENANCE_TUNNELS = ReadInt(f)
	CODE_DR_GEARS = ReadInt(f)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	z = ReadFloat(f)
	PositionEntity(me\Collider, x, y + 0.3, z)
	
	ResetEntity(me\Collider)
	ShowEntity(me\Collider)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	z = ReadFloat(f)
	PositionEntity(me\Head, x, y + 0.3, z)
	ResetEntity(me\Head)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	RotateEntity(me\Collider, x, y, 0.0)
	
	me\BlinkTimer = ReadFloat(f)
	me\BLINKFREQ = ReadFloat(f)
	me\BlinkEffect = ReadFloat(f)
	me\BlinkEffectTimer = ReadFloat(f)
	
	me\BlurTimer = ReadFloat(f)
	me\HealTimer = ReadFloat(f)
	
	me\Crouch = ReadByte(f)
	
	me\Stamina = ReadFloat(f)
	me\StaminaEffect = ReadFloat(f)
	me\StaminaEffectTimer = ReadFloat(f)
	
	me\EyeStuck = ReadFloat(f)
	me\EyeIrritation = ReadFloat(f)
	
	me\Injuries = ReadFloat(f)
	me\Bloodloss = ReadFloat(f)
	
	me\PrevInjuries = ReadFloat(f)
	me\PrevBloodloss = ReadFloat(f)
	
	msg\DeathMsg = ReadString(f)
	
	me\CurrFunds = ReadInt(f)
	me\UsedMastercard = ReadByte(f)
	
	me\VomitTimer = ReadFloat(f)
	me\Vomit = ReadByte(f)
	me\CameraShakeTimer = ReadFloat(f)
	
	me\CameraShake = ReadFloat(f)
	me\BigCameraShake = ReadFloat(f)
	
	me\LightBlink = ReadFloat(f)
	me\LightFlash = ReadFloat(f)
	
	me\Sanity = ReadFloat(f)
	
	me\RefinedItems = ReadInt(f)
	
	me\Pill2022Used = ReadFloat(f)
	EntityFX(pm\OBJ, (me\Pill2022Used > 2.0))
	me\Pill2022HealTimer = ReadFloat(f)
	
	I_005\ChanceToSpawn = ReadByte(f)
	
	For i = 0 To 6
		I_1025\State[i] = ReadFloat(f)
	Next
	For i = 0 To 4
		I_1025\FineState[i] = ReadFloat(f)
	Next
	
	I_008\Timer = ReadFloat(f)
	I_008\Revert = ReadByte(f)
	I_409\Timer = ReadFloat(f)
	I_409\Revert = ReadByte(f)
	
	I_035\Sad = ReadByte(f)
	
	Local DifficultyIndex% = ReadByte(f)
	
	SelectedDifficulty = difficulties[DifficultyIndex]
	If DifficultyIndex = ESOTERIC
		SelectedDifficulty\AggressiveNPCs = ReadByte(f)
		SelectedDifficulty\SaveType = ReadByte(f)
		SelectedDifficulty\OtherFactors = ReadByte(f)
		SelectedDifficulty\InventorySlots = ReadByte(f)
	EndIf
	
	MaxItemAmount = SelectedDifficulty\InventorySlots + (2 * (I_1025\FineState[0] > 0.0))
	Dim Inventory.Items(SelectedDifficulty\InventorySlots + 2)
	
	wi\GasMaskFogTimer = ReadFloat(f)
	
	wi\GasMask = ReadByte(f)
	wi\BallisticVest = ReadByte(f)
	wi\BallisticHelmet = ReadByte(f)
	wi\HazmatSuit = ReadByte(f)
	wi\NightVision = ReadByte(f)
	wi\SCRAMBLE = ReadByte(f)
	
	I_1499\Using = ReadByte(f)
	I_1499\PrevX = ReadFloat(f)
	I_1499\PrevY = ReadFloat(f)
	I_1499\PrevZ = ReadFloat(f)
	I_1499\x = ReadFloat(f)
	I_1499\y = ReadFloat(f)
	I_1499\z = ReadFloat(f)
	
	Local r1499_x# = ReadFloat(f)
	Local r1499_z# = ReadFloat(f)
	
	I_966\HasInsomnia = ReadFloat(f)
	I_966\InsomniaEffectTimer = ReadFloat(f)
	
	I_1048A\EarGrowTimer = ReadFloat(f)
	I_1048A\Revert = ReadByte(f)
	
	I_268\Using = ReadByte(f)
	I_268\Timer = ReadFloat(f)
	I_268\PauseCharge = ReadFloat(f)
	I_427\Using = ReadByte(f)
	I_427\Timer = ReadFloat(f)
	I_714\Using = ReadByte(f)
	I_294\Using = ReadByte(f)
	
	RandomSeed = ReadString(f)
	
	SecondaryLightOn = ReadFloat(f)
	LightVolume = ReadFloat(f)
	IsBlackOut = ReadByte(f)
	PrevIsBlackOut = ReadByte(f)
	
	RemoteDoorOn = ReadByte(f)
	
	SoundTransmission = ReadByte(f)
	
	KEY2_SPAWNRATE = ReadByte(f)
	
	Repeat
		Local Achv$ = ReadString(f)
		
		If Achv = "EOA" Then Exit
		S2IMapSet(UnlockedAchievements, Achv, True)
	Forever
	
	UsedConsole = ReadByte(f)
	
	MTFTimer = ReadFloat(f)
	
	Remove714Timer = ReadFloat(f)
	RemoveHazmatTimer = ReadFloat(f)
	
	CurrMapGrid.MapGrid = New MapGrid
	For x = 0 To MapGridSize
		For y = 0 To MapGridSize
			CurrMapGrid\Grid[x + (y * MapGridSize)] = ReadByte(f)
			CurrMapGrid\Found[x + (y * MapGridSize)] = ReadByte(f)
		Next
	Next
	
	If ReadInt(f) <> 113 Then RuntimeErrorEx(GetLocalString("save", "corrupted_1"))
	
	For n.NPCs = Each NPCs
		RemoveNPC(n)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local NPCType% = ReadByte(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		n.NPCs = CreateNPC(NPCType, x, y, z)
		Select NPCType
			Case NPCType173
				;[Block]
				n_I\Curr173 = n
				;[End Block]
			Case NPCType106
				;[Block]
				n_I\Curr106 = n
				;[End Block]
			Case NPCType096
				;[Block]
				n_I\Curr096 = n
				;[End Block]
			Case NPCType513_1
				;[Block]
				n_I\Curr513_1 = n
				;[End Block]
			Case NPCType049
				;[Block]
				n_I\Curr049 = n
				;[End Block]
			Case NPCType066
				;[Block]
				n_I\Curr066 = n
				;[End Block]
			Case NPCType999
				;[Block]
				n_I\Curr999 = n
				;[End Block]
		End Select
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		RotateEntity(n\Collider, x, y, z)
		
		n\State = ReadFloat(f)
		n\State2 = ReadFloat(f)
		n\State3 = ReadFloat(f)
		n\PrevState = ReadInt(f)
		
		n\Idle = ReadByte(f)
		n\LastDist = ReadFloat(f)
		n\LastSeen = ReadInt(f)
		
		n\CurrSpeed = ReadFloat(f)
		n\Angle = ReadFloat(f)
		n\Reload = ReadFloat(f)
		
		ForceSetNPCID(n, ReadInt(f))
		n\TargetID = ReadInt(f)
		
		n\EnemyX = ReadFloat(f)
		n\EnemyY = ReadFloat(f)
		n\EnemyZ = ReadFloat(f)
		
		n\Texture = ReadString(f)
		If n\Texture <> ""
			Tex = LoadTexture_Strict(n\Texture)
			EntityTexture(n\OBJ, Tex)
			DeleteSingleTextureEntryFromCache(Tex) : Tex = 0
		EndIf
		
		n\HasAsset = ReadByte(f)
		If n\HasAsset Then CreateNPCAsset(n)
		n\HasAnim = ReadByte(f)
		If n\HasAnim
			n\Frame = ReadFloat(f)
			SetAnimTime(n\OBJ, n\Frame)
		EndIf
		
		n\Contained = ReadByte(f)
		n\IsDead = ReadByte(f)
		n\HP = ReadInt(f)
		n\ModelScale = ReadFloat(f)
		If n\ModelScale > 0.0 Then ScaleEntity(n\OBJ, n\ModelScale, n\ModelScale, n\ModelScale)
		n\TextureID = ReadByte(f)
		If n\TextureID > 0 Then ChangeNPCTextureID(n, n\TextureID - 1)
		n\HideFromNVG = ReadByte(f)
		Select n\NPCType
			Case NPCTypeMTF
				;[Block]
				ID = ReadByte(f)
				If ID = 1
					n_I\MTFCoLeader = n
				ElseIf ID = 2
					n_I\MTFLeader = n
				EndIf
				;[End Block]
			Case NPCType1499_1
				;[Block]
				If n\LastSeen = 1 Then EntityColor(n\OBJ, 255.0, 204.0, 140.0) ; ~ I'm the king
				;[End Block]
		End Select
	Next
	If n_I\Curr999\State3 > 1
		EntityColor(n\OBJ, 255.0, 255.0, 140.0)
		EntityFX(n\OBJ, 1)
	EndIf
	
	For n.NPCs = Each NPCs
		If n\TargetID <> 0
			Local n2.NPCs
			
			For n2.NPCs = Each NPCs
				If n2 <> n
					If n2\ID = n\TargetID Then n\Target = n2
				EndIf
			Next
		EndIf
	Next
	
	If ReadInt(f) <> 632 Then RuntimeErrorEx(GetLocalString("save", "corrupted_2"))
	
	I_Zone\Transition[0] = ReadByte(f)
	I_Zone\Transition[1] = ReadByte(f)
	I_Zone\HasCustomForest = ReadByte(f)
	I_Zone\HasCustomMT = ReadByte(f)
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local RoomTemplateID% = ReadInt(f)
		Local Angle% = ReadInt(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Found% = ReadByte(f)
		Local Level% = ReadInt(f)
		
		Temp2 = ReadByte(f)
		
		Angle = WrapAngle(Angle)
		
		For rt.RoomTemplates = Each RoomTemplates
			If rt\ID = RoomTemplateID
				r.Rooms = CreateRoom(Level, rt\Shape, x, y, z, rt\RoomID, Angle)
				CalculateRoomExtents(r)
				;SetupTriggerBoxes(r)
				r\Found = Found
				Exit
			EndIf
		Next
		
		If Temp2 = 1 Then PlayerRoom = r
		
		For j = 0 To MaxRoomNPCs - 1
			ID = ReadInt(f)
			If ID > 0
				For n.NPCs = Each NPCs
					If n\ID = ID
						r\NPC[j] = n
						Exit
					EndIf
				Next
			EndIf
		Next
		
		For j = 0 To MaxRoomLevers - 1
			ID = ReadByte(f)
			If ID = 0
				RotateEntity(r\RoomLevers[j]\OBJ, 80.0, EntityYaw(r\RoomLevers[j]\OBJ), 0.0)
			ElseIf ID = 1
				RotateEntity(r\RoomLevers[j]\OBJ, -80.0, EntityYaw(r\RoomLevers[j]\OBJ), 0.0)
			EndIf
		Next
		
		If ReadByte(f) = 1 ; ~ This room has a grid
			If r\mt <> Null ; ~ Remove the old grid content
				DestroyMT(r\mt)
				Delete(r\mt)
			EndIf
			r\mt.MTGrid = New MTGrid
			
			For y = 0 To MTGridSize - 1
				For x = 0 To MTGridSize - 1
					r\mt\Grid[x + (y * MTGridSize)] = ReadByte(f)
					r\mt\Angles[x + (y * MTGridSize)] = ReadByte(f)
					; ~ Get only the necessary data, make the event handle the meshes and waypoints separately
				Next
			Next
		EndIf
		
		Local HasForest% = ReadByte(f)
		
		If HasForest > 0 ; ~ This room has a forest
			If r\fr <> Null ; ~ Remove the old forest
				DestroyForest(r\fr)
			Else
				r\fr.Forest = New Forest
			EndIf
			For y = 0 To ForestGridSize - 1
				For x = 0 To ForestGridSize - 1
					r\fr\Grid[x + (y * ForestGridSize)] = ReadByte(f)
				Next
			Next
			
			Local lX# = ReadFloat(f)
			Local lY# = ReadFloat(f)
			Local lZ# = ReadFloat(f)
			
			If HasForest = 1
				PlaceForest(r\fr, lX, lY, lZ, r)
			Else
				PlaceMapCreatorForest(r\fr, lX, lY, lZ, r)
			EndIf
		ElseIf r\fr <> Null ; ~ Remove the old forest
			DestroyForest(r\fr)
			Delete(r\fr)
		EndIf
	Next
	For r.Rooms = Each Rooms
		If r\x = r1499_x And r\z = r1499_z
			I_1499\PrevRoom = r
			Exit
		EndIf
	Next
	
	If ReadInt(f) <> 954 Then RuntimeErrorEx(GetLocalString("save", "corrupted_3"))
	
	For emit.Emitter = Each Emitter
		FreeEmitter(emit, True)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		ID = ReadInt(f)
		
		Temp2 = ReadByte(f)
		
		emit.Emitter = SetEmitter(Null, x, y, z, ID)
		emit\State = Temp2
		ForceSetEmitterID(emit, ReadInt(f))
	Next
	
	For r.Rooms = Each Rooms
		For j = 0 To MaxRoomEmitters - 1
			ID = ReadInt(f)
			If ID > 0
				For emit.Emitter = Each Emitter
					If emit\EmitterID = ID
						r\RoomEmitters[j] = emit
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	
	bk\IsBroken = ReadByte(f)
	bk\x = ReadFloat(f)
	bk\z = ReadFloat(f)
	
	Local Zone%, ShouldSpawnDoor%
	
	For y = MapGridSize To 0 Step -1
		If y < I_Zone\Transition[1] - (SelectedCustomMap = Null)
			Zone = 3
		ElseIf y >= I_Zone\Transition[1] - (SelectedCustomMap = Null) And y < I_Zone\Transition[0] - (SelectedCustomMap = Null)
			Zone = 2
		Else
			Zone = 1
		EndIf
		For x = MapGridSize To 0 Step -1
			If CurrMapGrid\Grid[x + (y * MapGridSize)] > MapGrid_NoTile
				For r.Rooms = Each Rooms
					r\Angle = WrapAngle(r\Angle)
					If Int(r\x / RoomSpacing) = x And Int(r\z / RoomSpacing) = y
						Select r\RoomTemplate\Shape
							Case ROOM1
								;[Block]
								ShouldSpawnDoor = (r\Angle = 90.0)
								;[End Block]
							Case ROOM2
								;[Block]
								ShouldSpawnDoor = (r\Angle = 90.0 Lor r\Angle = 270.0)
								;[End Block]
							Case ROOM2C
								;[Block]
								ShouldSpawnDoor = (r\Angle = 0.0 Lor r\Angle = 90.0)
								;[End Block]
							Case ROOM3
								;[Block]
								ShouldSpawnDoor = (r\Angle = 0.0 Lor r\Angle = 180.0 Lor r\Angle = 90.0)
								;[End Block]
							Default
								;[Block]
								ShouldSpawnDoor = True
								;[End Block]
						End Select
						If ShouldSpawnDoor
							If x + 1 < MapGridSize + 1
								If CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] > MapGrid_NoTile Then r\AdjDoor[0] = CreateDoor(r, Float(x) * RoomSpacing + (RoomSpacing / 2.0), 0.0, Float(y) * RoomSpacing, 90.0, Max(Rand(-3, 1), 0), ((Zone - 1) Mod 2) * 2)
							EndIf
						EndIf
						
						Select r\RoomTemplate\Shape
							Case ROOM1
								;[Block]
								ShouldSpawnDoor = (r\Angle = 180.0)
								;[End Block]
							Case ROOM2
								;[Block]
								ShouldSpawnDoor = (r\Angle = 0.0 Lor r\Angle = 180.0)
								;[End Block]
							Case ROOM2C
								;[Block]
								ShouldSpawnDoor = (r\Angle = 180.0 Lor r\Angle = 90.0)
								;[End Block]
							Case ROOM3
								;[Block]
								ShouldSpawnDoor = (r\Angle = 180.0 Lor r\Angle = 90.0 Lor r\Angle = 270.0)
								;[End Block]
							Default
								;[Block]
								ShouldSpawnDoor = True
								;[End Block]
						End Select
						If ShouldSpawnDoor
							If y + 1 < MapGridSize + 1
								If CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] > MapGrid_NoTile Then r\AdjDoor[3] = CreateDoor(r, Float(x) * RoomSpacing, 0.0, Float(y) * RoomSpacing + (RoomSpacing / 2.0), 0.0, Max(Rand(-3, 1), 0), ((Zone - 1) Mod 2) * 2)
							EndIf
						EndIf
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	
	Local TexDefault% = LoadTexture_Strict("GFX\Map\Textures\Door01_Corrosive.png")
	Local TexHeavy% = LoadTexture_Strict("GFX\Map\Textures\containment_doors_Corrosive.png")
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Open% = ReadByte(f)
		Local OpenState# = ReadFloat(f)
		Local Locked% = ReadByte(f)
		Local AutoClose% = ReadByte(f)
		
		Local OBJX# = ReadFloat(f)
		Local OBJZ# = ReadFloat(f)
		Local OBJYaw# = ReadFloat(f)
		
		Local OBJ2X# = ReadFloat(f)
		Local OBJ2Z# = ReadFloat(f)
		
		Local Timer# = ReadFloat(f)
		Local TimerState# = ReadFloat(f)
		
		Local IsElevDoor% = ReadByte(f)
		Local MTFClose% = ReadByte(f)
		
		Local IsAffected% = ReadByte(f)
		
		For d.Doors = Each Doors
			If EntityX(d\FrameOBJ, True) = x And EntityY(d\FrameOBJ, True) = y And EntityZ(d\FrameOBJ, True) = z
				d\Open = Open
				d\OpenState = OpenState
				d\Locked = Locked
				d\AutoClose = AutoClose
				d\Timer = Timer
				d\TimerState = TimerState
				d\IsElevatorDoor = IsElevDoor
				d\MTFClose = MTFClose
				d\IsAffected = IsAffected
				
				PositionEntity(d\OBJ, OBJX, y, OBJZ, True)
				If IsAffected
					Select d\DoorType
						Case DEFAULT_DOOR, ONE_SIDED_DOOR, ELEVATOR_DOOR
							;[Block]
							EntityTexture(d\OBJ, TexDefault)
							EntityTexture(d\FrameOBJ, TexDefault)
							;[End Block]
						Case HEAVY_DOOR
							;[Block]
							EntityTexture(d\OBJ, TexHeavy)
							EntityTexture(d\FrameOBJ, TexHeavy)
							;[End Block]
					End Select
				EndIf
				
				RotateEntity(d\OBJ, 0.0, OBJYaw, 0.0, True)
				If d\OBJ2 <> 0
					PositionEntity(d\OBJ2, OBJ2X, y, OBJ2Z, True)
					If IsAffected
						Select d\DoorType
							Case DEFAULT_DOOR, ONE_SIDED_DOOR, ELEVATOR_DOOR
								;[Block]
								EntityTexture(d\OBJ2, TexDefault)
								;[End Block]
							Case HEAVY_DOOR
								;[Block]
								EntityTexture(d\OBJ2, TexHeavy)
								;[End Block]
						End Select
					EndIf
				EndIf
				Exit
			EndIf
		Next
	Next
	DeleteSingleTextureEntryFromCache(TexDefault) : TexDefault = 0
	DeleteSingleTextureEntryFromCache(TexHeavy) : TexHeavy = 0
	
	If ReadInt(f) <> 1845 Then RuntimeErrorEx(GetLocalString("save", "corrupted_4"))
	
	Local de.Decals
	
	For de.Decals = Each Decals
		RemoveDecal(de)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		ID = ReadInt(f)
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Pitch# = ReadFloat(f)
		Local Yaw# = ReadFloat(f)
		Local Roll# = ReadFloat(f)
		
		de.Decals = CreateDecal(ID, x, y, z, Pitch, Yaw, Roll)
		
		Local Size# = ReadFloat(f)
		Local MaxSize# = ReadFloat(f)
		Local Alpha# = ReadFloat(f)
		Local FX% = ReadByte(f)
		Local BlendMode% = ReadByte(f)
		Local Red% = ReadByte(f), Green% = ReadByte(f), Blue% = ReadByte(f)
		
		Local DecalTimer# = ReadFloat(f)
		Local LifeTime# = ReadFloat(f)
		Local SizeChange# = ReadFloat(f)
		Local AlphaChange# = ReadFloat(f)
		
		For de.Decals = Each Decals
			If EntityX(de\OBJ, True) = x And EntityY(de\OBJ, True) = y And EntityZ(de\OBJ, True) = z
				de\Size = Size
				de\MaxSize = MaxSize
				de\Alpha = Alpha
				de\FX = FX
				de\BlendMode = BlendMode
				de\R = Red : de\G = Green : de\B = Blue
				de\Timer = DecalTimer
				de\LifeTime = LifeTime
				de\SizeChange = SizeChange
				de\AlphaChange = AlphaChange
				
				ScaleEntity(de\OBJ, Size, Size, 1.0, True)
				EntityAlpha(de\OBJ, Alpha)
				EntityFX(de\OBJ, FX)
				EntityBlend(de\OBJ, BlendMode)
				If Red <> 0 Lor Green <> 0 Lor Blue <> 0 Then EntityColor(de\OBJ, Red, Green, Blue)
				Exit
			EndIf
		Next
	Next
	
	Local e.Events, ch.Chunk, chp.ChunkPart
	
	For e.Events = Each Events
		RemoveEvent(e)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		e.Events = New Events
		e\EventID = ReadByte(f)
		e\EventState = ReadFloat(f)
		e\EventState2 = ReadFloat(f)
		e\EventState3 = ReadFloat(f)
		e\EventState4 = ReadFloat(f)
		x = ReadFloat(f)
		z = ReadFloat(f)
		For r.Rooms = Each Rooms
			If EntityX(r\OBJ) = x And EntityZ(r\OBJ) = z
				e\room = r
				Exit
			EndIf
		Next
		e\EventStr = ReadString(f)
		FindEventVariable(e)
	Next
	For e.Events = Each Events
		Select e\EventID
			Case e_dimension_1499
				;[Block]
				If e\EventState > 0.0
					HideChunks()
					For ch.Chunk = Each Chunk
						RemoveChunk(ch)
					Next
					For chp.ChunkPart = Each ChunkPart
						RemoveChunkPart(chp)
					Next
					FreeEntity(I_1499\Sky) : I_1499\Sky = 0
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType1499_1
							If n\InFacility = Floor1499 Then RemoveNPC(n)
						EndIf
					Next
					
					Local du.Dummy1499_1
					
					For du.Dummy1499_1 = Each Dummy1499_1
						RemoveDummy1499_1(du)
					Next
					
					e\EventStr = ""
					e\EventState = 0.0
				EndIf
				;[End Block]
			Case e_cont1_205
				;[Block]
				e\EventStr = ""
				;[End Block]
			Case e_cont1_106
				;[Block]
				If e\EventState2 = 0.0 Then PositionEntity(e\room\Objects[1], EntityX(e\room\Objects[1], True), (-1280.0) * RoomScale, EntityZ(e\room\Objects[1], True), True)
				;[End Block]
			Case e_cont2_008
				;[Block]
				If e\EventState < 2.0 Then RotateEntity(e\room\Objects[1], 85.0, EntityYaw(e\room\Objects[1], True), 0.0, True)
				;[End Block]
		End Select
	Next
	
	Local it.Items, itt.ItemTemplates
	
	For it.Items = Each Items
		RemoveItem(it)
	Next
	ItemAmount = 0
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local IttName$ = ReadString(f)
		
		ID = ReadInt(f)
		
		Local Name$ = ReadString(f)
		Local DisplayName$ = ReadString(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Red = ReadByte(f)
		Green = ReadByte(f)
		Blue = ReadByte(f)
		
		Alpha = ReadFloat(f)
		
		it.Items = CreateItem(IttName, ID, x, y, z, Red, Green, Blue, Alpha)
		it\Name = Name
		it\DisplayName = DisplayName
		
		EntityType(it\Collider, HIT_ITEM)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		RotateEntity(it\Collider, x, y, 0.0)
		
		it\State = ReadFloat(f)
		it\State2 = ReadFloat(f)
		it\State3 = ReadFloat(f)
		it\Picked = ReadByte(f)
		If it\Picked Then HideEntity(it\Collider)
		
		Local nt% = ReadByte(f)
		
		If nt = True Then SelectedItem = it
		
		nt = ReadByte(f)
		If nt < 66
			Inventory(nt) = it
			ItemAmount = ItemAmount + 1
		EndIf
		
		For itt.ItemTemplates = Each ItemTemplates
			If itt\ID = ID And itt\Name = IttName; And itt\DisplayName = DisplayName ; ~ Not sure about that
				If itt\IsAnim
					SetAnimTime(it\OBJ, ReadFloat(f))
					Exit
				EndIf
			EndIf
		Next
		it\InvSlots = ReadByte(f)
		it\ID = ReadInt(f)
		
		If it\ID > LastItemID Then LastItemID = it\ID
		
		If ReadByte(f) = 0
			it\InvImg = it\ItemTemplate\InvImg
		Else
			it\InvImg = it\ItemTemplate\InvImg2
		EndIf
	Next
	
	Local ij.Items
	Local o_i%
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		o_i = ReadInt(f)
		
		For ij.Items = Each Items
			If ij\ID = o_i
				it.Items = ij
				Exit
			EndIf
		Next
		For j = 0 To it\InvSlots - 1
			o_i = ReadInt(f)
			If o_i <> -1
				For ij.Items = Each Items
					If ij\ID = o_i
						it\SecondInv[j] = ij
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	
	For it.Items = Each Items
		If it\ItemTemplate\ID = it_e_reader Lor it\ItemTemplate\ID = it_e_reader20 Lor it\ItemTemplate\ID = it_e_readerulti
			it\EReaderPageAmount = ReadByte(f)
			For o_i = 1 To it\EReaderPageAmount
				Name = ReadString(f)
				For itt.ItemTemplates = Each ItemTemplates
					If itt\Name = Name
						it\EReaderPage[o_i] = itt
						Exit
					EndIf
				Next
			Next
		EndIf
	Next
	
	For itt.ItemTemplates = Each ItemTemplates
		itt\Found = ReadByte(f)
	Next
	
	For emit.Emitter = Each Emitter
		For r.Rooms = Each Rooms
			ID = ReadByte(f)
			If ID = 1
				emit\room = r
				EntityParent(emit\Owner, r\OBJ)
			EndIf
		Next
	Next
	
	EscapeTimer = ReadInt(f)
	
	Local sc.SecurityCams
	
	For sc.SecurityCams = Each SecurityCams
		sc\CoffinEffect = ReadByte(f)
		sc\PlayerState = ReadInt(f)
	Next
	
	CloseFile(f)
	
	If wi\NightVision > 0
		fog\FarDist = 15.0
	ElseIf wi\SCRAMBLE > 0
		fog\FarDist = 9.0
	Else
		fog\FarDist = 6.0
	EndIf
	
	For i = 0 To 1
		mon_I\UpdateCheckpoint[i] = True
	Next
	
	For r.Rooms = Each Rooms
		For i = 0 To MaxRoomAdjacents - 1
			r\Adjacent[i] = Null
		Next
		
		Local r2.Rooms
		
		For r2.Rooms = Each Rooms
			If r <> r2
				If r2\z = r\z
					If r2\x = r\x + 8.0
						r\Adjacent[0] = r2
						If r\AdjDoor[0] = Null Then r\AdjDoor[0] = r2\AdjDoor[2]
					ElseIf r2\x = r\x - 8.0
						r\Adjacent[2] = r2
						If r\AdjDoor[2] = Null Then r\AdjDoor[2] = r2\AdjDoor[0]
					EndIf
				ElseIf r2\x = r\x
					If r2\z = r\z - 8.0
						r\Adjacent[1] = r2
						If r\AdjDoor[1] = Null Then r\AdjDoor[1] = r2\AdjDoor[3]
					ElseIf r2\z = r\z + 8.0
						r\Adjacent[3] = r2
						If r\AdjDoor[3] = Null Then r\AdjDoor[3] = r2\AdjDoor[1]
					EndIf
				EndIf
			EndIf
			If r\Adjacent[0] <> Null And r\Adjacent[1] <> Null And r\Adjacent[2] <> Null And r\Adjacent[3] <> Null Then Exit
		Next
		
		For d.Doors = Each Doors
			If d\KeyCard = 0 And d\Code = 0
				If EntityZ(d\FrameOBJ, True) = r\z
					If EntityX(d\FrameOBJ, True) = r\x + 4.0
						r\AdjDoor[0] = d
					ElseIf EntityX(d\FrameOBJ, True) = r\x - 4.0
						r\AdjDoor[2] = d
					EndIf
				ElseIf EntityX(d\FrameOBJ, True) = r\x
					If EntityZ(d\FrameOBJ, True) = r\z + 4.0
						r\AdjDoor[3] = d
					ElseIf EntityZ(d\FrameOBJ, True) = r\z - 4.0
						r\AdjDoor[1] = d
					EndIf
				EndIf
			EndIf
		Next
	Next
	
	If PlayerRoom\RoomTemplate\RoomID = r_dimension_1499
		me\BlinkTimer = -1.0
		TeleportToRoom(I_1499\PrevRoom)
	EndIf
	
	CatchErrors("Uncaught: LoadGame(" + File + ")")
End Function

Function LoadGameQuick%(File$)
	CatchErrors("LoadGameQuick(" + File + ")")
	
	Local r.Rooms, n.NPCs, d.Doors, emit.Emitter
	Local x#, y#, z#, i%, j%, Temp% = 0, Temp2% = 0, StrTemp$ = "", ID%, Tex%
	Local SF%, b%, t1%
	Local Player_X#, Player_Y#, Player_Z#
	Local f% = ReadFile_Strict(SavePath + File + "\save.cb")
	
	GameSaved = True
	msg\Txt = ""
	msg\Timer = 0.0
	msg\HintTxt = ""
	msg\HintTimer = 0.0
	me\Zombie = False
	me\Deaf = False
	me\DeafTimer = 0.0
	me\Playable = True
	me\SelectedEnding = -1
	
	ReadString(f)
	ReadString(f)
	
	StrTemp = ReadString(f)
	If StrTemp <> VersionNumber Then RuntimeErrorEx(Format(Format(GetLocalString("save", "imcompatible"), StrTemp, "{0}"), VersionNumber, "{1}"))
	
	ReadByte(f)
	ReadString(f)
	ReadString(f)
	
	me\DropSpeed = -0.1
	me\HeadDropSpeed = 0.0
	me\CurrSpeed = 0.0
	me\Shake = 0.0
	me\HeartBeatVolume = 0.0
	
	me\StopHidingTimer = 0.0
	
	me\Terminated = False
	me\DeathTimer = 0.0
	me\FallTimer = 0.0
	MenuOpen = False
	
	ClearConsole()
	ClearCheats()
	WireFrameState = 0
	WireFrame(0)
	
	ReadInt(f)
	ReadInt(f)
	ReadInt(f)
	ReadInt(f)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	z = ReadFloat(f)
	PositionEntity(me\Collider, x, y + 0.3, z)
	
	ResetEntity(me\Collider)
	ShowEntity(me\Collider)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	z = ReadFloat(f)
	PositionEntity(me\Head, x, y + 0.3, z)
	ResetEntity(me\Head)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	RotateEntity(me\Collider, x, y, 0.0)
	
	me\BlinkTimer = ReadFloat(f)
	me\BLINKFREQ = ReadFloat(f)
	me\BlinkEffect = ReadFloat(f)
	me\BlinkEffectTimer = ReadFloat(f)
	
	me\BlurTimer = ReadFloat(f)
	me\HealTimer = ReadFloat(f)
	
	me\Crouch = ReadByte(f)
	
	me\Stamina = ReadFloat(f)
	me\StaminaEffect = ReadFloat(f)
	me\StaminaEffectTimer = ReadFloat(f)
	
	me\EyeStuck = ReadFloat(f)
	me\EyeIrritation = ReadFloat(f)
	
	me\Injuries = ReadFloat(f)
	me\Bloodloss = ReadFloat(f)
	
	me\PrevInjuries = ReadFloat(f)
	me\PrevBloodloss = ReadFloat(f)
	
	msg\DeathMsg = ReadString(f)
	
	me\CurrFunds = ReadInt(f)
	me\UsedMastercard = ReadByte(f)
	
	me\VomitTimer = ReadFloat(f)
	me\Vomit = ReadByte(f)
	me\CameraShakeTimer = ReadFloat(f)
	
	me\CameraShake = ReadFloat(f)
	me\BigCameraShake = ReadFloat(f)
	
	me\LightBlink = ReadFloat(f)
	me\LightFlash = ReadFloat(f)
	
	me\Sanity = ReadFloat(f)
	
	me\RefinedItems = ReadInt(f)
	
	me\Pill2022Used = ReadFloat(f)
	EntityFX(pm\OBJ, (me\Pill2022Used > 2.0))
	me\Pill2022HealTimer = ReadFloat(f)
	
	I_005\ChanceToSpawn = ReadByte(f)
	
	For i = 0 To 6
		I_1025\State[i] = ReadFloat(f)
	Next
	For i = 0 To 4
		I_1025\FineState[i] = ReadFloat(f)
	Next
	
	I_008\Timer = ReadFloat(f)
	I_008\Revert = ReadByte(f)
	I_409\Timer = ReadFloat(f)
	I_409\Revert = ReadByte(f)
	
	I_035\Sad = ReadByte(f)
	
	Local DifficultyIndex% = ReadByte(f)
	
	SelectedDifficulty = difficulties[DifficultyIndex]
	If DifficultyIndex = ESOTERIC
		SelectedDifficulty\AggressiveNPCs = ReadByte(f)
		SelectedDifficulty\SaveType = ReadByte(f)
		SelectedDifficulty\OtherFactors = ReadByte(f)
		SelectedDifficulty\InventorySlots = ReadByte(f)
	EndIf
	
	MaxItemAmount = SelectedDifficulty\InventorySlots + (2 * (I_1025\FineState[0] > 0.0))
	Dim Inventory.Items(SelectedDifficulty\InventorySlots + 2)
	
	wi\GasMaskFogTimer = ReadFloat(f)
	
	wi\GasMask = ReadByte(f)
	wi\BallisticVest = ReadByte(f)
	wi\BallisticHelmet = ReadByte(f)
	wi\HazmatSuit = ReadByte(f)
	wi\NightVision = ReadByte(f)
	wi\SCRAMBLE = ReadByte(f)
	
	I_1499\Using = ReadByte(f)
	I_1499\PrevX = ReadFloat(f)
	I_1499\PrevY = ReadFloat(f)
	I_1499\PrevZ = ReadFloat(f)
	I_1499\x = ReadFloat(f)
	I_1499\y = ReadFloat(f)
	I_1499\z = ReadFloat(f)
	
	Local r1499_x# = ReadFloat(f)
	Local r1499_z# = ReadFloat(f)
	
	I_966\HasInsomnia = ReadFloat(f)
	I_966\InsomniaEffectTimer = ReadFloat(f)
	
	I_1048A\EarGrowTimer = ReadFloat(f)
	I_1048A\Revert = ReadByte(f)
	
	I_268\Using = ReadByte(f)
	I_268\Timer = ReadFloat(f)
	I_268\PauseCharge = ReadFloat(f)
	I_427\Using = ReadByte(f)
	I_427\Timer = ReadFloat(f)
	I_714\Using = ReadByte(f)
	I_294\Using = ReadByte(f)
	
	RandomSeed = ReadString(f)
	
	SecondaryLightOn = ReadFloat(f)
	LightVolume = ReadFloat(f)
	IsBlackOut = ReadByte(f)
	PrevIsBlackOut = ReadByte(f)
	
	RemoteDoorOn = ReadByte(f)
	
	SoundTransmission = ReadByte(f)
	
	KEY2_SPAWNRATE = ReadByte(f)
	
	ClearS2IMap(UnlockedAchievements)
	Repeat
		Local Achv$ = ReadString(f)
		
		If Achv = "EOA" Then Exit
		S2IMapSet(UnlockedAchievements, Achv, True)
	Forever
	
	UsedConsole = ReadByte(f)
	
	MTFTimer = ReadFloat(f)
	
	Remove714Timer = ReadFloat(f)
	RemoveHazmatTimer = ReadFloat(f)
	
	For x = 0 To MapGridSize
		For y = 0 To MapGridSize
			CurrMapGrid\Grid[x + (y * MapGridSize)] = ReadByte(f)
			CurrMapGrid\Found[x + (y * MapGridSize)] = ReadByte(f)
		Next
	Next
	
	; ~ Unparent all emitters before deleting parent objects
	For emit.Emitter = Each Emitter
		EntityParent(emit\Owner, 0)
	Next
	
	Local shdw.Shadows
	
	If ReadInt(f) <> 113 Then RuntimeErrorEx(GetLocalString("save", "corrupted_1"))
	
	For n.NPCs = Each NPCs
		RemoveNPC(n)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local NPCType% = ReadByte(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		n.NPCs = CreateNPC(NPCType, x, y, z)
		Select NPCType
			Case NPCType173
				;[Block]
				n_I\Curr173 = n
				;[End Block]
			Case NPCType106
				;[Block]
				n_I\Curr106 = n
				;[End Block]
			Case NPCType096
				;[Block]
				n_I\Curr096 = n
				;[End Block]
			Case NPCType513_1
				;[Block]
				n_I\Curr513_1 = n
				;[End Block]
			Case NPCType049
				;[Block]
				n_I\Curr049 = n
				;[End Block]
			Case NPCType066
				;[Block]
				n_I\Curr066 = n
				;[End Block]
			Case NPCType999
				;[Block]
				n_I\Curr999 = n
				;[End Block]
		End Select
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		RotateEntity(n\Collider, x, y, z)
		
		n\State = ReadFloat(f)
		n\State2 = ReadFloat(f)
		n\State3 = ReadFloat(f)
		n\PrevState = ReadInt(f)
		
		n\Idle = ReadByte(f)
		n\LastDist = ReadFloat(f)
		n\LastSeen = ReadInt(f)
		
		n\CurrSpeed = ReadFloat(f)
		n\Angle = ReadFloat(f)
		n\Reload = ReadFloat(f)
		
		ForceSetNPCID(n, ReadInt(f))
		n\TargetID = ReadInt(f)
		
		n\EnemyX = ReadFloat(f)
		n\EnemyY = ReadFloat(f)
		n\EnemyZ = ReadFloat(f)
		
		n\Texture = ReadString(f)
		If n\Texture <> ""
			Tex = LoadTexture_Strict(n\Texture)
			EntityTexture(n\OBJ, Tex)
			DeleteSingleTextureEntryFromCache(Tex) : Tex = 0
		EndIf
		
		n\HasAsset = ReadByte(f)
		If n\HasAsset Then CreateNPCAsset(n)
		n\HasAnim = ReadByte(f)
		If n\HasAnim
			n\Frame = ReadFloat(f)
			SetAnimTime(n\OBJ, n\Frame)
		EndIf
		
		n\Contained = ReadByte(f)
		n\IsDead = ReadByte(f)
		n\HP = ReadInt(f)
		n\ModelScale = ReadFloat(f)
		If n\ModelScale > 0.0 Then ScaleEntity(n\OBJ, n\ModelScale, n\ModelScale, n\ModelScale)
		n\TextureID = ReadByte(f)
		If n\TextureID > 0 Then ChangeNPCTextureID(n, n\TextureID - 1)
		n\HideFromNVG = ReadByte(f)
		Select n\NPCType
			Case NPCTypeMTF
				;[Block]
				ID = ReadByte(f)
				If ID = 1
					n_I\MTFCoLeader = n
				ElseIf ID = 2
					n_I\MTFLeader = n
				EndIf
				;[End Block]
			Case NPCType1499_1
				;[Block]
				If n\LastSeen = 1 Then EntityColor(n\OBJ, 255.0, 204.0, 140.0) ; ~ I'm the king
				;[End Block]
		End Select
	Next
	If n_I\Curr999\State3 > 1
		EntityColor(n\OBJ, 255.0, 255.0, 140.0)
		EntityFX(n\OBJ, 1)
	EndIf
	
	For n.NPCs = Each NPCs
		If n\TargetID <> 0
			Local n2.NPCs
			
			For n2.NPCs = Each NPCs
				If n2 <> n
					If n2\ID = n\TargetID Then n\Target = n2
				EndIf
			Next
		EndIf
	Next
	
	If ReadInt(f) <> 632 Then RuntimeErrorEx(GetLocalString("save", "corrupted_2"))
	
	I_Zone\Transition[0] = ReadByte(f)
	I_Zone\Transition[1] = ReadByte(f)
	I_Zone\HasCustomForest = ReadByte(f)
	I_Zone\HasCustomMT = ReadByte(f)
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local RoomTemplateID% = ReadInt(f)
		Local Angle% = ReadInt(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Found% = ReadByte(f)
		Local Level% = ReadInt(f)
		
		Temp2 = ReadByte(f)
		
		If Angle >= 360.0 Then Angle = Angle - 360.0
		
		For r.Rooms = Each Rooms
			If r\x = x And r\z = z Then Exit
		Next
		
		If Temp2 = 1 Then PlayerRoom = r
		
		For j = 0 To MaxRoomNPCs - 1
			ID = ReadInt(f)
			If ID > 0
				For n.NPCs = Each NPCs
					If n\ID = ID
						r\NPC[j] = n
						Exit
					EndIf
				Next
			EndIf
		Next
		
		For j = 0 To MaxRoomLevers - 1
			ID = ReadByte(f)
			If ID = 0
				RotateEntity(r\RoomLevers[j]\OBJ, 80.0, EntityYaw(r\RoomLevers[j]\OBJ), 0.0)
			ElseIf ID = 1
				RotateEntity(r\RoomLevers[j]\OBJ, -80.0, EntityYaw(r\RoomLevers[j]\OBJ), 0.0)
			EndIf
		Next
		
		If ReadByte(f) = 1 ; ~ This room has a grid
			For y = 0 To MTGridSize - 1
				For x = 0 To MTGridSize - 1
					ReadByte(f)
					ReadByte(f)
				Next
			Next
		ElseIf r\mt <> Null ; ~ Remove the old grid
			DestroyMT(r\mt)
			Delete(r\mt)
		EndIf
		
		If ReadByte(f) > 0 ; ~ This room has a forest
			For y = 0 To ForestGridSize - 1
				For x = 0 To ForestGridSize - 1
					ReadByte(f)
				Next
			Next
			
			Local lX# = ReadFloat(f)
			Local lY# = ReadFloat(f)
			Local lZ# = ReadFloat(f)
		ElseIf r\fr <> Null ; ~ Remove the old forest
			DestroyForest(r\fr)
			Delete(r\fr)
		EndIf
	Next
	For r.Rooms = Each Rooms
		If r\x = r1499_x And r\z = r1499_z
			I_1499\PrevRoom = r
			Exit
		EndIf
	Next
	
	If ReadInt(f) <> 954 Then RuntimeErrorEx(GetLocalString("save", "corrupted_3"))
	
	For emit.Emitter = Each Emitter
		FreeEmitter(emit, True)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		ID = ReadInt(f)
		
		Temp2 = ReadByte(f)
		
		emit.Emitter = SetEmitter(r, x, y, z, ID)
		emit\State = Temp2
		ForceSetEmitterID(emit, ReadInt(f))
	Next
	For r.Rooms = Each Rooms
		For j = 0 To MaxRoomEmitters - 1
			ID = ReadInt(f)
			If ID > 0
				For emit.Emitter = Each Emitter
					If emit\EmitterID = ID
						r\RoomEmitters[j] = emit
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	
	bk\IsBroken = ReadByte(f)
	bk\x = ReadFloat(f)
	bk\z = ReadFloat(f)
	
	Local TexCorrDefault% = LoadTexture_Strict("GFX\Map\Textures\Door01_Corrosive.png")
	Local TexCorrHeavy% = LoadTexture_Strict("GFX\Map\Textures\containment_doors_Corrosive.png")
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Open% = ReadByte(f)
		Local OpenState# = ReadFloat(f)
		Local Locked% = ReadByte(f)
		Local AutoClose% = ReadByte(f)
		
		Local OBJX# = ReadFloat(f)
		Local OBJZ# = ReadFloat(f)
		Local OBJYaw# = ReadFloat(f)
		
		Local OBJ2X# = ReadFloat(f)
		Local OBJ2Z# = ReadFloat(f)
		
		Local Timer# = ReadFloat(f)
		Local TimerState# = ReadFloat(f)
		
		Local IsElevDoor% = ReadByte(f)
		Local MTFClose% = ReadByte(f)
		
		Local IsAffected% = ReadByte(f)
		
		For d.Doors = Each Doors
			If EntityX(d\FrameOBJ, True) = x And EntityY(d\FrameOBJ, True) = y And EntityZ(d\FrameOBJ, True) = z
				d\Open = Open
				d\OpenState = OpenState
				d\Locked = Locked
				d\AutoClose = AutoClose
				d\Timer = Timer
				d\TimerState = TimerState
				d\IsElevatorDoor = IsElevDoor
				d\MTFClose = MTFClose
				d\IsAffected = IsAffected
				
				PositionEntity(d\OBJ, OBJX, y, OBJZ, True)
				If IsAffected
						Select d\DoorType
						Case DEFAULT_DOOR, ONE_SIDED_DOOR, ELEVATOR_DOOR
							;[Block]
							EntityTexture(d\OBJ, TexCorrDefault)
							EntityTexture(d\FrameOBJ, TexCorrDefault)
							;[End Block]
						Case HEAVY_DOOR
							;[Block]
							EntityTexture(d\OBJ, TexCorrHeavy)
							EntityTexture(d\FrameOBJ, TexCorrHeavy)
							;[End Block]
					End Select
				EndIf
				RotateEntity(d\OBJ, 0.0, OBJYaw, 0.0, True)
				
				If d\OBJ2 <> 0
					PositionEntity(d\OBJ2, OBJ2X, y, OBJ2Z, True)
					If IsAffected
						Select d\DoorType
							Case DEFAULT_DOOR, ONE_SIDED_DOOR, ELEVATOR_DOOR
								;[Block]
								EntityTexture(d\OBJ2, TexCorrDefault)
								;[End Block]
							Case HEAVY_DOOR
								;[Block]
								EntityTexture(d\OBJ2, TexCorrHeavy)
								;[End Block]
						End Select
					EndIf
				EndIf
				Exit
			EndIf
		Next
	Next
	DeleteSingleTextureEntryFromCache(TexCorrDefault) : TexCorrDefault = 0
	DeleteSingleTextureEntryFromCache(TexCorrHeavy) : TexCorrHeavy = 0
	
	If ReadInt(f) <> 1845 Then RuntimeErrorEx(GetLocalString("save", "corrupted_4"))
	
	Local de.Decals
	
	For de.Decals = Each Decals
		RemoveDecal(de)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		ID = ReadInt(f)
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Pitch# = ReadFloat(f)
		Local Yaw# = ReadFloat(f)
		Local Roll# = ReadFloat(f)
		
		de.Decals = CreateDecal(ID, x, y, z, Pitch, Yaw, Roll)
		
		Local Size# = ReadFloat(f)
		Local MaxSize# = ReadFloat(f)
		Local Alpha# = ReadFloat(f)
		Local FX% = ReadByte(f)
		Local BlendMode% = ReadByte(f)
		Local Red% = ReadByte(f), Green% = ReadByte(f), Blue% = ReadByte(f)
		
		Local DecalTimer# = ReadFloat(f)
		Local LifeTime# = ReadFloat(f)
		Local SizeChange# = ReadFloat(f)
		Local AlphaChange# = ReadFloat(f)
		
		For de.Decals = Each Decals
			If EntityX(de\OBJ, True) = x And EntityY(de\OBJ, True) = y And EntityZ(de\OBJ, True) = z
				de\Size = Size
				de\MaxSize = MaxSize
				de\Alpha = Alpha
				de\FX = FX
				de\BlendMode = BlendMode
				de\R = Red : de\G = Green : de\B = Blue
				de\Timer = DecalTimer
				de\LifeTime = LifeTime
				de\SizeChange = SizeChange
				de\AlphaChange = AlphaChange
				
				ScaleEntity(de\OBJ, Size, Size, 1.0, True)
				EntityAlpha(de\OBJ, Alpha)
				EntityFX(de\OBJ, FX)
				EntityBlend(de\OBJ, BlendMode)
				If Red <> 0 Lor Green <> 0 Lor Blue <> 0 Then EntityColor(de\OBJ, Red, Green, Blue)
				Exit
			EndIf
		Next
	Next
	
	Local e.Events, ch.Chunk, chp.ChunkPart
	
	For e.Events = Each Events
		RemoveEvent(e)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		e.Events = New Events
		e\EventID = ReadByte(f)
		e\EventState = ReadFloat(f)
		e\EventState2 = ReadFloat(f)
		e\EventState3 = ReadFloat(f)
		e\EventState4 = ReadFloat(f)
		x = ReadFloat(f)
		z = ReadFloat(f)
		For r.Rooms = Each Rooms
			If EntityX(r\OBJ) = x And EntityZ(r\OBJ) = z
				e\room = r
				Exit
			EndIf
		Next
		e\EventStr = ReadString(f)
		FindEventVariable(e)
	Next
	For e.Events = Each Events
		Select e\EventID
			Case e_cont1_173
				;[Block]
				; ~ A hacky fix for the case that the intro objects aren't loaded when they should
				; ~ Altough I'm too lazy to add those objects there because at the time where you can save, those objects are already in the ground anyway -- ENDSHN
				If e\room\Objects[0] = 0
					e\room\Objects[0] = CreatePivot()
					e\room\Objects[1] = CreatePivot()
				EndIf
				;[End Block]
			Case e_dimension_1499
				;[Block]
				If e\EventState = 1.0
					HideChunks()
					For ch.Chunk = Each Chunk
						RemoveChunk(ch)
					Next
					For chp.ChunkPart = Each ChunkPart
						RemoveChunkPart(chp)
					Next
					FreeEntity(I_1499\Sky) : I_1499\Sky = 0
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType1499_1
							If n\InFacility = Floor1499 Then RemoveNPC(n)
						EndIf
					Next
					
					Local du.Dummy1499_1
					
					For du.Dummy1499_1 = Each Dummy1499_1
						RemoveDummy1499_1(du)
					Next
					
					e\EventStr = ""
					e\EventState = 0.0
				EndIf
				;[End Block]
			Case e_cont2_860_1
				;[Block]
				If e\EventState = 1.0 Then ShowEntity(e\room\fr\Forest_Pivot)
				;[End Block]
			Case e_cont2_008
				;[Block]
				If e\EventState < 2.0 Then RotateEntity(e\room\Objects[1], 85.0, EntityYaw(e\room\Objects[1], True), 0.0, True)
				;[End Block]
			Case e_cont2_1123
				;[Block]
				If e\room\Objects[7] = 0
					e\room\Objects[7] = LoadRMesh("GFX\Map\cont2_1123_cell.rmesh", Null)
					ScaleEntity(e\room\Objects[7], RoomScale, RoomScale, RoomScale)
					PositionEntity(e\room\Objects[7], e\room\x, e\room\y, e\room\z)
					RotateEntity(e\room\Objects[7], 0.0, e\room\Angle, 0.0)
					EntityParent(e\room\Objects[7], e\room\OBJ)
					HideEntity(e\room\Objects[7])
				EndIf
				;[End Block]
			Case e_cont2_012
				;[Block]
				; ~ Reset SCP-012's texture
				If e\EventState2 < 70.0 * 31.0
					Tex = LoadTexture_Strict("GFX\Map\Textures\scp_012(1).png")
					EntityTexture(e\room\Objects[3], Tex)
					DeleteSingleTextureEntryFromCache(Tex)
				EndIf
				;[End Block]
			Case e_cont1_079
				;[Block]
				If e\EventState = 1.0 Then HideEntity(e\room\Objects[1])
				;[End Block]
			Case e_cont1_106
				;[Block]
				SetAnimTime(e\room\Objects[5], 1.0 + 119.0 * (e\EventState <> 0.0))
				;[End Block]
			Case e_room2_6_ez_789_j
				;[Block]
				SetAnimTime(e\room\Objects[0], 1.0 + 239.0 * (e\EventState = 2.0))
				;[End Block]
			Case r_gate_a ; ~ Erase endings stuff
				;[Block]
				If e\room\Objects[0] <> 0
					FreeEntity(e\room\Objects[0]) : e\room\Objects[0] = 0
					FreeEntity(e\room\Objects[4]) : e\room\Objects[4] = 0
					FreeEntity(e\room\Objects[5]) : e\room\Objects[5] = 0
					FreeEntity(e\room\Objects[7]) : e\room\Objects[7] = 0
					FreeEntity(e\room\Objects[8]) : e\room\Objects[8] = 0
					FreeEntity(e\room\Objects[13]) : e\room\Objects[13] = 0
				EndIf
				If e\room\Objects[9] <> 0
					FreeEntity(e\room\Objects[12]) : e\room\Objects[12] = 0
					FreeEntity(e\room\Objects[11]) : e\room\Objects[11] = 0
					FreeEntity(e\room\Objects[10]) : e\room\Objects[10] = 0
					FreeEntity(e\room\Objects[9]) : e\room\Objects[9] = 0
				EndIf
				;[End Block]
			Case r_gate_b
				;[Block]
				If e\room\Objects[0] <> 0 Then FreeEntity(e\room\Objects[0]) : e\room\Objects[0] = 0
				If e\room\Objects[6] <> 0 Then FreeEntity(e\room\Objects[6]) : e\room\Objects[6] = 0
				;[End Block]
		End Select
	Next
	
	Local it.Items, itt.ItemTemplates
	
	For it.Items = Each Items
		RemoveItem(it)
	Next
	ItemAmount = 0
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local IttName$ = ReadString(f)
		
		ID = ReadInt(f)
		
		Local Name$ = ReadString(f)
		Local DisplayName$ = ReadString(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Red = ReadByte(f)
		Green = ReadByte(f)
		Blue = ReadByte(f)
		
		Alpha = ReadFloat(f)
		
		it.Items = CreateItem(IttName, ID, x, y, z, Red, Green, Blue, Alpha)
		it\Name = Name
		it\DisplayName = DisplayName
		
		EntityType(it\Collider, HIT_ITEM)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		RotateEntity(it\Collider, x, y, 0.0)
		
		it\State = ReadFloat(f)
		it\State2 = ReadFloat(f)
		it\State3 = ReadFloat(f)
		it\Picked = ReadByte(f)
		If it\Picked Then HideEntity(it\Collider)
		
		Local nt% = ReadByte(f)
		
		If nt = True Then SelectedItem = it
		
		nt = ReadByte(f)
		If nt < 66
			Inventory(nt) = it
			ItemAmount = ItemAmount + 1
		EndIf
		
		For itt.ItemTemplates = Each ItemTemplates
			If itt\ID = ID And itt\Name = IttName; And itt\DisplayName = DisplayName ; ~ Not sure about that
				If itt\IsAnim
					SetAnimTime(it\OBJ, ReadFloat(f))
					Exit
				EndIf
			EndIf
		Next
		it\InvSlots = ReadByte(f)
		it\ID = ReadInt(f)
		
		If it\ID > LastItemID Then LastItemID = it\ID
		
		If ReadByte(f) = 0
			it\InvImg = it\ItemTemplate\InvImg
		Else
			it\InvImg = it\ItemTemplate\InvImg2
		EndIf
	Next
	
	Local ij.Items
	Local o_i%
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		o_i = ReadInt(f)
		
		For ij.Items = Each Items
			If ij\ID = o_i
				it.Items = ij
				Exit
			EndIf
		Next
		For j = 0 To it\InvSlots - 1
			o_i = ReadInt(f)
			If o_i <> -1
				For ij.Items = Each Items
					If ij\ID = o_i
						it\SecondInv[j] = ij
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	
	For it.Items = Each Items
		If it\ItemTemplate\ID = it_e_reader Lor it\ItemTemplate\ID = it_e_reader20 Lor it\ItemTemplate\ID = it_e_readerulti
			it\EReaderPageAmount = ReadByte(f)
			For o_i = 1 To it\EReaderPageAmount
				Name = ReadString(f)
				For itt.ItemTemplates = Each ItemTemplates
					If itt\Name = Name
						it\EReaderPage[o_i] = itt
						Exit
					EndIf
				Next
			Next
		EndIf
	Next
	
	For itt.ItemTemplates = Each ItemTemplates
		itt\Found = ReadByte(f)
	Next
	
	For emit.Emitter = Each Emitter
		For r.Rooms = Each Rooms
			ID = ReadByte(f)
			If ID = 1
				emit\room = r
				EntityParent(emit\Owner, r\OBJ)
			EndIf
		Next
	Next
	
	EscapeTimer = ReadInt(f)
	
	; ~ This will hopefully fix the SCP-895 crash bug after the player died by it's sanity effect and then quickloaded the game -- ENDSHN
	Local sc.SecurityCams
	
	For sc.SecurityCams = Each SecurityCams
		sc\CoffinEffect = ReadByte(f)
		sc\PlayerState = ReadInt(f)
	Next
	
	CloseFile(f)
	
	ClearFogColor()
	
	d_I\AnimButton = 0
	
	If wi\GasMask = 0 Then HideEntity(t\OverlayID[1])
	If wi\HazmatSuit = 0 Then HideEntity(t\OverlayID[2])
	
	If wi\NightVision > 0
		fog\FarDist = 15.0
	ElseIf wi\SCRAMBLE > 0
		fog\FarDist = 9.0
	Else
		fog\FarDist = 6.0
	EndIf
	
	; ~ Free some entities that could potentially cause memory leaks (for the endings)
	; ~ This is only required for the LoadGameQuick function, as the other one is from the menu where everything is already deleted anyways
	Local xTemp#, zTemp#
	
	If Sky <> 0 Then FreeEntity(Sky) : Sky = 0
	For r.Rooms = Each Rooms
		Select r\RoomTemplate\RoomID
			Case r_cont1_035
				;[Block]
				Update035Label(r\Objects[4])
				;[End Block]
		End Select
	Next
	
	; ~ Resetting some stuff (those get changed when going to some areas)
	HideDistance = 17.0
	
	Tex = LoadTexture_Strict("GFX\NPCs\D_9341.png")
	EntityTexture(pm\OBJ, Tex)
	DeleteSingleTextureEntryFromCache(Tex)
	
	CatchErrors("Uncaught: LoadGameQuick(" + File + ")")
End Function

Global GameSaved%
Global CanSave%

Function UpdateSaveState%()
	If SelectedDifficulty\SaveType <> NO_SAVES
		CanSave = 3
		If QuickLoadPercent > -1 Lor me\FallTimer < 0.0 Lor (Not me\Playable) Then CanSave = 0
	EndIf
End Function

Type AutoSave
	Field Amount%
	Field Timer#
End Type

Global as.AutoSave

Function UpdateAutoSave%()
	If (Not opt\AutoSaveEnabled) Lor SelectedDifficulty\SaveType <> SAVE_ANYWHERE Lor me\Terminated Lor CanSave < 3 Lor (Not me\Playable) Lor me\Zombie
		If as\Timer <= 70.0 * 5.0 Then CancelAutoSave()
		Return
	EndIf
	
	If as\Timer <= 0.0
		as\Amount = as\Amount + 1
		If as\Amount >= 5 Then as\Amount = 0
		SaveGame(CurrSave\Name + "_" + as\Amount)
	Else
		as\Timer = as\Timer - fps\Factor[0]
		If as\Timer <= 70.0 * 5.0 Then CreateHintMsg(Format(GetLocalString("save", "autosave.in"), Str(Int(Ceil(as\Timer) / 70.0))))
	EndIf
End Function

Function CancelAutoSave%()
	CreateHintMsg(GetLocalString("save", "autosave.canceled"))
	as\Timer = 70.0 * 70.0
End Function

Function SaveAchievementsFile%()
	Local File$
	
	File = WriteFile(GetEnv("AppData") + "\scpcb-ue\Data\Does the Black Moon howl.cb")
	WriteByte(File, S2IMapContains(UnlockedAchievements, "keter"))
	WriteByte(File, S2IMapContains(UnlockedAchievements, "apollyon"))
	WriteByte(File, SNAVUnlocked)
	WriteByte(File, EReaderUnlocked)
	CloseFile(File)
End Function

Function LoadAchievementsFile%()
	; ~ Go out of function immediately if the file doesn't exist!
	If FileType(GetEnv("AppData") + "\scpcb-ue\Data\Does the Black Moon howl.cb") <> 1 Then Return
	
	Local File$
	
	File = OpenFile(GetEnv("AppData") + "\scpcb-ue\Data\Does the Black Moon howl.cb")
	If ReadByte(File) Then S2IMapSet(UnlockedAchievements, "keter", True)
	If ReadByte(File) Then S2IMapSet(UnlockedAchievements, "apollyon", True)
	If ReadByte(File) Then SNAVUnlocked = True
	If ReadByte(File) Then EReaderUnlocked = True
	CloseFile(File)
End Function

Type Save
	Field Name$
	Field Time$
	Field Date$
	Field Version$
	Field Seed$
	Field Difficulty$
End Type

Global CurrSave.Save
Global DelSave.Save
Global SavedGamesAmount%

Function LoadSavedGames%()
	CatchErrors("LoadSaveGames()")
	
	Local sv.Save, newsv.Save
	
	For sv.Save = Each Save
		Delete(sv)
	Next
	SavedGamesAmount = 0
	
	If FileType(SavePath) = 1 Then RuntimeErrorEx(Format(GetLocalString("save", "cantcreatedir"), SavePath))
	If FileType(SavePath) = 0 Then CreateDir(SavePath)
	
	Local SaveDir% = ReadDir(SavePath)
	
	NextFile(SaveDir) : NextFile(SaveDir) ; ~ Skipping "." and ".."
	
	Local File$ = NextFile(SaveDir)
	
	While File <> ""
		If FileType(SavePath + File) = 2
			Local f% = ReadFile_Strict(SavePath + File + "\save.cb")
			
			newsv.Save = New Save
			newsv\Name = File
			
			newsv\Time = ReadString(f)
			newsv\Date = ReadString(f)
			newsv\Version = ReadString(f)
			If ReadByte(f) = 0
				newsv\Seed = ReadString(f)
			Else
				newsv\Seed = "mc_" + ReadString(f)
			EndIf
			newsv\Difficulty = ReadString(f)
			
			CloseFile(f)
			SavedGamesAmount = SavedGamesAmount + 1
		EndIf
		File = NextFile(SaveDir)
	Wend
	CloseDir(SaveDir)
	
	CatchErrors("Uncaught: LoadSaveGames()")
End Function

Function DeleteGame%(sv.Save)
	sv\Name = SavePath + sv\Name + "\"
	
	Local DelDir% = ReadDir(sv\Name)
	
	If DelDir <> 0
		NextFile(DelDir) : NextFile(DelDir) ; ~ Skipping "." and ".."
		
		Local File$ = NextFile(DelDir)
		
		While File <> ""
			DeleteFile(sv\Name + File)
			File = NextFile(DelDir)
		Wend
		CloseDir(DelDir)
		DeleteDir(sv\Name)
		SavedGamesAmount = SavedGamesAmount - 1
	EndIf
	Delete(sv)
End Function

Const CustomMapsPath$ = "Map Creator\Maps\"

Type CustomMaps
	Field Name$
	Field Author$
End Type

Global CurrCustomMap.CustomMaps
Global DelCustomMap.CustomMaps
Global SelectedCustomMap.CustomMaps
Global CustomMapsAmount%

Function LoadCustomMaps%()
	CatchErrors("LoadCustomMaps()")
	
	Local cm.CustomMaps, newcm.CustomMaps
	
	For cm.CustomMaps = Each CustomMaps
		Delete(cm)
	Next
	CustomMapsAmount = 0
	
	If FileType(CustomMapsPath) = 1 Then RuntimeErrorEx(Format(GetLocalString("save", "cantcreatedir"), CustomMapsPath))
	If FileType(CustomMapsPath) = 0 Then CreateDir(CustomMapsPath)
	
	Local MapDir% = ReadDir(CustomMapsPath)
	
	NextFile(MapDir) : NextFile(MapDir) ; ~ Skipping "." and ".."
	
	Local File$ = NextFile(MapDir)
	
	While File <> ""
		If FileType(CustomMapsPath + File) = 1
			If Right(File, 6) = "cbmap2" Lor Right(File, 5) = "cbmap"
				Local f% = ReadFile_Strict(CustomMapsPath + File)
				
				newcm.CustomMaps = New CustomMaps
				newcm\Name = File
				If Right(File, 6) = "cbmap2"
					newcm\Author = ReadLine(f)
				Else
					newcm\Author = GetLocalString("creator", "unknown")
				EndIf
				CloseFile(f)
				CustomMapsAmount = CustomMapsAmount + 1
			EndIf
		EndIf
		File = NextFile(MapDir)
	Wend
	CloseDir(MapDir)
	
	CatchErrors("Uncaught: LoadCustomMaps()")
End Function

Function DeleteCustomMap%(cm.CustomMaps)
	Local DelDir% = ReadDir(CustomMapsPath)
	
	If DelDir <> 0
		NextFile(DelDir) : NextFile(DelDir) ; ~ Skipping "." and ".."
		
		Local File$ = NextFile(DelDir)
		
		While File <> ""
			DeleteFile(CustomMapsPath + File)
			File = NextFile(DelDir)
		Wend
		CloseDir(DelDir)
		CustomMapsAmount = CustomMapsAmount - 1
	EndIf
	Delete(cm)
End Function

Function LoadMap%(File$)
	CatchErrors("LoadMap(" + File + ")")
	
	Local r.Rooms, rt.RoomTemplates, e.Events
	Local f%, x%, y%, Name$, ID%, Angle%, Prob#
	Local RoomsAmount%, ForestPieceAmount%, MTPieceAmount%, i%
	
	f = ReadFile_Strict(File)
	
	Delete(CurrMapGrid)
	CurrMapGrid = New MapGrid
	
	If Right(File, 6) = "cbmap2"
		ReadLine(f)
		ReadLine(f)
		I_Zone\Transition[0] = ReadByte(f)
		I_Zone\Transition[1] = ReadByte(f)
		RoomsAmount = ReadInt(f)
		ForestPieceAmount = ReadInt(f)
		MTPieceAmount = ReadInt(f)
		
		I_Zone\HasCustomForest = (ForestPieceAmount > 0)
		
		I_Zone\HasCustomMT = (MTPieceAmount > 0)
		
		; ~ Facility rooms
		For i = 0 To RoomsAmount - 1
			x = ReadByte(f)
			y = ReadByte(f)
			Name = Lower(ReadString(f))
			ID = FindRoomID(Name)
			
			Angle = ReadByte(f) * 90.0
			
			For rt.RoomTemplates = Each RoomTemplates
				If rt\RoomID = ID
					If Angle <> 90.0 And Angle <> 270.0 Then Angle = Angle + 180.0
					Angle = WrapAngle(Angle)
					r.Rooms = CreateRoom(0, rt\Shape, (MapGridSize - x) * RoomSpacing, 0.0, y * RoomSpacing, ID, Angle)
					CalculateRoomExtents(r)
					;SetupTriggerBoxes(r)
					CurrMapGrid\Grid[(MapGridSize - x) + (y * MapGridSize)] = MapGrid_Tile
					Exit
				EndIf
			Next
			
			Name = ReadString(f)
			ID = FindEventID(Name)
			Prob = ReadFloat(f)
			
			If r <> Null
				If Prob > 0.0
					If Rnd(0.0, 1.0) <= Prob
						e.Events = New Events
						e\EventID = ID
						FindEventVariable(e)
						e\room = r
					EndIf
				ElseIf Prob = 0.0 And Name <> ""
					e.Events = New Events
					e\EventID = ID
					FindEventVariable(e)
					e\room = r
				EndIf
			EndIf
		Next
		
		Local ForestRoom.Rooms
		
		For r.Rooms = Each Rooms
			If r\RoomTemplate\RoomID = r_cont2_860_1
				ForestRoom = r
				Exit
			EndIf
		Next
		
		Local fr.Forest
		
		If ForestRoom <> Null Then fr.Forest = New Forest
		
		; ~ Forest rooms
		For i = 0 To ForestPieceAmount - 1
			x = ReadByte(f)
			y = ReadByte(f)
			Name = ReadString(f)
			
			Angle = ReadByte(f)
			If Angle <> 0.0 And Angle <> 2.0 Then Angle = Angle + 2.0
			Angle = Angle + 1.0
			If Angle > 3.0 Then Angle = (Angle Mod 4.0)
			x = (ForestGridSize - 1) - x
			
			If fr <> Null
				Select Name
					; ~ 1, 2, 3, 4 = ROOM1
					; ~ 5, 6, 7, 8 = ROOM2
					; ~ 9, 10, 11, 12 = ROOM2C
					; ~ 13, 14, 15, 16 = ROOM3
					; ~ 17, 18, 19, 20 = ROOM4
					; ~ 21, 22, 23, 24 = DOORROOM
					Case "scp-860-1 endroom"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 1.0
						;[End Block]
					Case "scp-860-1 path"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 5.0
						;[End Block]
					Case "scp-860-1 corner"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 9.0
						;[End Block]
					Case "scp-860-1 t-shaped path"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 13.0
						;[End Block]
					Case "scp-860-1 4-way path"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 17.0
						;[End Block]
					Case "scp-860-1 door"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 21.0
						;[End Block]
				End Select
			EndIf
		Next
		
		If fr <> Null
			ForestRoom\fr = fr
			PlaceMapCreatorForest(ForestRoom\fr, ForestRoom\x, ForestRoom\y + 30.0, ForestRoom\z, ForestRoom)
		EndIf
		
		Local MTRoom.Rooms
		
		For r.Rooms = Each Rooms
			If r\RoomTemplate\RoomID = r_room2_mt
				MTRoom = r
				Exit
			EndIf
		Next
		
		If MTRoom <> Null Then MTRoom\mt = New MTGrid
		
		; ~ Maintenance tunnels rooms
		For i = 0 To MTPieceAmount - 1
			x = ReadByte(f)
			y = ReadByte(f)
			Name = ReadString(f)
			
			Angle = ReadByte(f)
			If Angle <> 1.0 And Angle <> 3.0 Then Angle = Angle + 2.0
			If Name = "maintenance tunnel corner" Lor Name = "maintenance tunnel t-shaped room" Then Angle = Angle + 3.0
			If Angle > 3.0 Then Angle = (Angle Mod 4.0)
			
			x = (MTGridSize - 1) - x
			
			If MTRoom <> Null
				Select Name
					Case "maintenance tunnel endroom"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM1 + 1
						;[End Block]
					Case "maintenance tunnel corridor"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM2 + 1
						;[End Block]
					Case "maintenance tunnel corner"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM2C + 1
						;[End Block]
					Case "maintenance tunnel t-shaped room"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM3 + 1
						;[End Block]
					Case "maintenance tunnel 4-way room"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM4 + 1
						;[End Block]
					Case "maintenance tunnel elevator"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM4 + 2
						;[End Block]
					Case "maintenance tunnel generator room"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM4 + 3
						;[End Block]
				End Select
				MTRoom\mt\Angles[x + (y * MTGridSize)] = Angle
			EndIf
		Next
	Else
		I_Zone\Transition[0] = 13
		I_Zone\Transition[1] = 7
		I_Zone\HasCustomForest = False
		I_Zone\HasCustomMT = False
		While (Not Eof(f))
			x = ReadByte(f)
			y = ReadByte(f)
			Name = Lower(ReadString(f))
			ID = FindRoomID(Name)
			
			Angle = ReadByte(f) * 90.0
			
			For rt.RoomTemplates = Each RoomTemplates
				If rt\RoomID = ID
					If Angle <> 90.0 And Angle <> 270.0 Then Angle = Angle + 180.0
					Angle = WrapAngle(Angle)
					
					r.Rooms = CreateRoom(0, rt\Shape, (MapGridSize - x) * RoomSpacing, 0.0, y * RoomSpacing, ID, Angle)
					CalculateRoomExtents(r)
					;SetupTriggerBoxes(r)
					
					CurrMapGrid\Grid[(MapGridSize - x) + (y * MapGridSize)] = MapGrid_Tile
					Exit
				EndIf
			Next
			
			Name = ReadString(f)
			ID = FindEventID(Name)
			Prob = ReadFloat(f)
			
			If r <> Null
				If Prob > 0.0
					If Rnd(0.0, 1.0) <= Prob
						e.Events = New Events
						e\EventID = ID
						FindEventVariable(e)
						e\room = r
					EndIf
				ElseIf Prob = 0.0 And Name <> ""
					e.Events = New Events
					e\EventID = ID
					FindEventVariable(e)
					e\room = r
				EndIf
			EndIf
		Wend
	EndIf
	
	CloseFile(f)
	
	Local d.Doors
	Local Zone%
	Local ShouldSpawnDoor%
	
	For y = MapGridSize To 0 Step -1
		If y < I_Zone\Transition[1]
			Zone = 3
		ElseIf y >= I_Zone\Transition[1] And y < I_Zone\Transition[0]
			Zone = 2
		Else
			Zone = 1
		EndIf
		For x = MapGridSize To 0 Step -1
			If CurrMapGrid\Grid[x + (y * MapGridSize)] > MapGrid_NoTile
				For r.Rooms = Each Rooms
					r\Angle = WrapAngle(r\Angle)
					If Int(r\x / RoomSpacing) = x And Int(r\z / RoomSpacing) = y
						Select r\RoomTemplate\Shape
							Case ROOM1
								;[Block]
								ShouldSpawnDoor = (r\Angle = 90.0)
								;[End Block]
							Case ROOM2
								;[Block]
								ShouldSpawnDoor = (r\Angle = 90.0 Lor r\Angle = 270.0)
								;[End Block]
							Case ROOM2C
								;[Block]
								ShouldSpawnDoor = (r\Angle = 0.0 Lor r\Angle = 90.0)
								;[End Block]
							Case ROOM3
								;[Block]
								ShouldSpawnDoor = (r\Angle = 0.0 Lor r\Angle = 180.0 Lor r\Angle = 90.0)
								;[End Block]
							Default
								;[Block]
								ShouldSpawnDoor = True
								;[End Block]
						End Select
						If ShouldSpawnDoor
							If x + 1 < MapGridSize + 1
								If CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] > MapGrid_NoTile Then r\AdjDoor[0] = CreateDoor(r, Float(x) * RoomSpacing + (RoomSpacing / 2.0), 0.0, Float(y) * RoomSpacing, 90.0, Max(Rand(-3, 1), 0), ((Zone - 1) Mod 2) * 2)
							EndIf
						EndIf
						
						Select r\RoomTemplate\Shape
							Case ROOM1
								;[Block]
								ShouldSpawnDoor = (r\Angle = 180.0)
								;[End Block]
							Case ROOM2
								;[Block]
								ShouldSpawnDoor = (r\Angle = 0.0 Lor r\Angle = 180.0)
								;[End Block]
							Case ROOM2C
								;[Block]
								ShouldSpawnDoor = (r\Angle = 180.0 Lor r\Angle = 90.0)
								;[End Block]
							Case ROOM3
								;[Block]
								ShouldSpawnDoor = (r\Angle = 180.0 Lor r\Angle = 90.0 Lor r\Angle = 270.0)
								;[End Block]
							Default
								;[Block]
								ShouldSpawnDoor = True
								;[End Block]
						End Select
						If ShouldSpawnDoor
							If y + 1 < MapGridSize + 1
								If CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] > MapGrid_NoTile Then r\AdjDoor[3] = CreateDoor(r, Float(x) * RoomSpacing, 0.0, Float(y) * RoomSpacing + (RoomSpacing / 2.0), 0.0, Max(Rand(-3, 1), 0), ((Zone - 1) Mod 2) * 2)
							EndIf
						EndIf
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	
	; ~ Spawn some rooms outside the map
	r.Rooms = CreateRoom(0, ROOM1, 0.0, 500.0, -(RoomSpacing) * 10.0, r_gate_b)
	CalculateRoomExtents(r)
	CreateEvent(e_gate_b, r_gate_b, 0)
	
	r.Rooms = CreateRoom(0, ROOM1, 0.0, 500.0, -(RoomSpacing) * 2.0, r_gate_a)
	CalculateRoomExtents(r)
	CreateEvent(e_gate_a, r_gate_a, 0)
	
	r.Rooms = CreateRoom(0, ROOM1, (MapGridSize + 2) * RoomSpacing, 0.0, (MapGridSize + 2) * RoomSpacing, r_dimension_106)
	CalculateRoomExtents(r)
	CreateEvent(e_dimension_106, r_dimension_106, 0) 
	
	If opt\IntroEnabled
		r.Rooms = CreateRoom(0, ROOM1, RoomSpacing, 250.0, (MapGridSize + 2) * RoomSpacing, r_cont1_173_intro)
		CalculateRoomExtents(r)
		CreateEvent(e_cont1_173_intro, r_cont1_173_intro, 0)
	EndIf
	
	r.Rooms = CreateRoom(0, ROOM1, -(RoomSpacing) * 2.0, 800.0, 0.0, r_dimension_1499)
	CalculateRoomExtents(r)
	CreateEvent(e_dimension_1499, r_dimension_1499, 0)
	
	For r.Rooms = Each Rooms
		For i = 0 To MaxRoomAdjacents - 1
			r\Adjacent[i] = Null
		Next
		
		Local r2.Rooms
		
		For r2.Rooms = Each Rooms
			If r <> r2
				If r2\z = r\z
					If r2\x = r\x + 8.0
						r\Adjacent[0] = r2
						If r\AdjDoor[0] = Null Then r\AdjDoor[0] = r2\AdjDoor[2]
					ElseIf r2\x = r\x - 8.0
						r\Adjacent[2] = r2
						If r\AdjDoor[2] = Null Then r\AdjDoor[2] = r2\AdjDoor[0]
					EndIf
				ElseIf r2\x = r\x
					If r2\z = r\z - 8.0
						r\Adjacent[1] = r2
						If r\AdjDoor[1] = Null Then r\AdjDoor[1] = r2\AdjDoor[3]
					ElseIf r2\z = r\z + 8.0
						r\Adjacent[3] = r2
						If r\AdjDoor[3] = Null Then r\AdjDoor[3] = r2\AdjDoor[1]
					EndIf
				EndIf
			EndIf
			If r\Adjacent[0] <> Null And r\Adjacent[1] <> Null And r\Adjacent[2] <> Null And r\Adjacent[3] <> Null Then Exit
		Next
	Next
	
	CatchErrors("Uncaught: LoadMap(" + File + ")")
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D TSS