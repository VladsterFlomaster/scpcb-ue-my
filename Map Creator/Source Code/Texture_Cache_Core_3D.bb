; ~ Texture Cache Constants
;[Block]
Const MapTexturesFolder$ = "GFX\map\textures\"

Const DeleteMapTextures% = 0
Const DeleteAllTextures% = 1
;[End Block]

Type TextureInCache
	Field Tex%
	Field TexName$
	Field TexDeleteType%
End Type

Function LoadTextureCheckingIfInCache%(TexName$, TexFlags% = 1, DeleteType% = DeleteMapTextures)
	If TexName = "" Then Return(0)
	
	Local tic.TextureInCache
	
	For tic.TextureInCache = Each TextureInCache
		If tic\TexName <> "CreateTexture"
			If StripPath(TexName) = tic\TexName
				If tic\TexDeleteType < DeleteType Then tic\TexDeleteType = DeleteType
				Return(tic\Tex)
			EndIf
		EndIf
	Next
	
	Local CurrPath$ = TexName
	
	tic.TextureInCache = New TextureInCache
	tic\TexName = StripPath(TexName)
	tic\TexDeleteType = DeleteType
	If FileType(LanguagePath + CurrPath) = 1 Then CurrPath = LanguagePath + CurrPath
	If tic\Tex = 0 Then tic\Tex = LoadTexture(CurrPath, TexFlags)
	Return(tic\Tex)
End Function

Function DeleteTextureEntriesFromCache%(DeleteType%)
	Local tic.TextureInCache
	
	For tic.TextureInCache = Each TextureInCache
		If tic\TexDeleteType =< DeleteType
			If tic\Tex <> 0 Then FreeTexture(tic\Tex) : tic\Tex = 0
			Delete(tic)
		EndIf
	Next
End Function

Function DeleteSingleTextureEntryFromCache%(Texture%)
	Local tic.TextureInCache
	
	For tic.TextureInCache = Each TextureInCache
		If tic\Tex = Texture
			If tic\Tex <> 0 Then FreeTexture(tic\Tex) : tic\Tex = 0
			Delete(tic)
		EndIf
	Next
End Function

Function CreateTextureUsingCacheSystem%(Width%, Height%, TexFlags% = 1, Frames% = 1, DeleteType% = DeleteAllTextures)
	Local tic.TextureInCache
	
	tic.TextureInCache = New TextureInCache
	tic\TexName = "CreateTexture"
	tic\TexDeleteType = DeleteType
	tic\Tex = CreateTexture(Width, Height, TexFlags + 256, Frames)
	Return(tic\Tex)
End Function

Function IsTexAlpha%(Tex%, Name$ = "") ; ~ Detect transparency in textures
	Local mat.Materials
	Local Temp1s$
	Local Temp%, Temp2%
	
	If Name = ""
		Temp1s = StripPath(TextureName(Tex))
	Else
		Temp1s = Name
	EndIf
	
	; ~ Texture is a lightmap
	If Instr(Temp1s, "_lm") <> 0 Then Return(2)
	
	For mat.Materials = Each Materials
		If mat\Name = Temp1s
			Temp = mat\IsDiffuseAlpha
			Temp2 = mat\UseMask
			Exit
		EndIf
	Next
	Return(1 + (2 * (Temp <> 0)) + (4 * (Temp2 <> 0)))
End Function

; ~ This is supposed to be the only texture that will be outside the TextureCache system
Global MissingTexture%

Function LoadMissingTexture%()
	MissingTexture = CreateTexture(1 , 1, 1 + 256)
	TextureBlend(MissingTexture, 3)
	SetBuffer(TextureBuffer(MissingTexture))
	ClsColor(0, 0, 0)
	Cls()
	SetBuffer(BackBuffer())
End Function

Function CheckForTexture%(Tex%, TexFlags% = 1)
	Local Name$ = ""
	
	If FileType(TextureName(Tex)) = 1 ; ~ Check if texture is existing in original path
		Name = TextureName(Tex)
	ElseIf FileType(MapTexturesFolder + StripPath(TextureName(Tex))) = 1 ; ~ If not, check the MapTexturesFolder
		Name = MapTexturesFolder + StripPath(TextureName(Tex))
	EndIf
	
	Local Texture% = LoadTextureCheckingIfInCache(Name, TexFlags)
	
	If Texture <> 0
		If ((TexFlags Shr 1) Mod 2) = 0
			TextureBlend(Texture, 5)
		Else
			TextureBlend(Texture, 1)
		EndIf
	EndIf
	Return(Texture)
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D TSS