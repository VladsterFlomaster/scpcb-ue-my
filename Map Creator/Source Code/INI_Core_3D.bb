; ~ IniController - A part of BlitzToolBox
; ~ Write & Read ini file.
; ~ v1.06 2022.11.12
; ~ https://github.com/ZiYueCommentary/BlitzToolbox

Function IniWriteBuffer%(File$, ClearPrevious% = True)
	IniWriteBuffer_(File, ClearPrevious)
End Function

Function IniGetBufferString$(File$, Section$, Parameter$, DefaultValue$ = "")
	Return(IniGetBufferString_(File, Section, Parameter, DefaultValue))
End Function

Function IniGetString$(File$, Section$, Parameter$, DefaultValue$ = "", AllowBuffer% = True)
	Return(IniGetString_(File, Section, Parameter, DefaultValue, AllowBuffer))
End Function

Function IniGetInt%(File$, Section$, Parameter$, DefaultValue% = 0, AllowBuffer% = True)
	Local Result$ = IniGetString(File, Section, Parameter, DefaultValue, AllowBuffer)
	
	Select Result
		Case "True", "true", "1"
			;[Block]
			Return(True)
			;[End Block]
		Case "False", "false", "0"
			;[Block]
			Return(False)
			;[End Block]
		Default
			;[Block]
			Return(Int(Result))
			;[End Block]
	End Select
End Function

Function IniGetFloat#(File$, Section$, Parameter$, DefaultValue# = 0.0, AllowBuffer% = True)
	Return(IniGetFloat_(File, Section, Parameter, DefaultValue, AllowBuffer))
End Function

Function GetLocalString$(Section$, Parameter$, CheckRootFile% = True)
	Local DefaultValue$
	
	If CheckRootFile
		DefaultValue = IniGetBufferString("..\" + LanguageFile, Section, Parameter, Section + "," + Parameter)
	Else 
		DefaultValue = Section + "," + Parameter
	EndIf
	Return(IniGetBufferString("..\" + LanguagePath + LanguageFile, Section, Parameter, DefaultValue))
End Function

Function Format$(String_$, Parameter$, Replace_$ = "%s")
	Return(Replace(String_, Replace_, Parameter))
End Function

Function StripFileName$(File$)
	Local LastSlash% = 0
	Local FileLen% = Len(File)
	Local i%
	
	If FileLen = 0 Then Return("")
	
	For i = FileLen To 1 Step -1
		Local Middle$ = Mid(File, i, 1)
		
		If Middle = "\" Lor Middle = "/" ; ~ Detect a delimiter
			LastSlash = i
			Exit
		EndIf
	Next
	Return(Left(File, LastSlash))
End Function

Function StripPath$(File$)
	Local LastSlash% = 0
	Local FileLen% = Len(File)
	Local i%
	
	If FileLen = 0 Then Return("")
	
	For i = FileLen To 1 Step -1
		Local Middle$ = Mid(File, i, 1)
		
		If Middle = "\" Lor Middle = "/" ; ~ Detect a delimiter
			LastSlash = i
			Exit
		EndIf
	Next
	Return(Right(File, FileLen - LastSlash))
End Function

Function StripAbsolutePath$(File$, Dir$)
	Local Pos% = Instr(Lower(File), Dir)
	
	If Pos > 0 Then File = Mid(File, Pos)
	
	Return(File)
End Function

Global OptionFileMC$ = GetEnv("AppData") + "\scpcb-ue\Data\options_MC.ini"

Type Options
	Field FogR%, FogG%, FogB%
	Field CursorR%, CursorB%, CursorG%
	Field VSync%
	Field ShowFPS%
	Field CamRange#
	Field TotalVidMemory%, TotalPhysMemory%
End Type

Global opt.Options = New Options

Function LoadOptionsINI%()
	; ~ [3-D SCENE]
	
	; ~ TODO: Make colored fog
	opt\FogR = IniGetInt(OptionFileMC, "3-D Scene", "BG Color R", 0)
	
	opt\FogG = IniGetInt(OptionFileMC, "3-D Scene", "BG Color G", 0)
	
	opt\FogB = IniGetInt(OptionFileMC, "3-D Scene", "BG Color B", 0)
	
	opt\CursorR = IniGetInt(OptionFileMC, "3-D Scene", "Cursor Color R", 255)
	
	opt\CursorG = IniGetInt(OptionFileMC, "3-D Scene", "Cursor Color G", 0)
	
	opt\CursorB = IniGetInt(OptionFileMC, "3-D Scene", "Cursor Color B", 0)
	
	opt\VSync = IniGetInt(OptionFileMC, "3-D Scene", "VSync", True)
	
	opt\ShowFPS = IniGetInt(OptionFileMC, "3-D Scene", "Show FPS", False)
	
	opt\CamRange = IniGetFloat(OptionFileMC, "3-D Scene", "Camera Range", 50.0)
End Function

opt\TotalVidMemory = TotalVidMem()
opt\TotalPhysMemory = TotalPhys()

;~IDEal Editor Parameters:
;~C#Blitz3D TSS