If you experience a MAV error during loading/launching the game (or low FPS), and the video driver name in the error window includes terms like "UHD Graphics [number]"
move three files from dgVoodoo folder into the game folder (the one where the .exe file is located). This should help.

If you are still having problems running the game, try downloading the DirectX packages:
https://www.microsoft.com/en-US/download/details.aspx?id=35
https://www.techpowerup.com/download/visual-c-redistributable-runtime-package-all-in-one/

After these adjustments, the game may run properly but with some minor issues (e.g., gamma problems). Please, contact us if you have any questions (links are available in the game launcher).
Thanks for understanding.

If you wish to use more than 2GB of VRAM then edit dgVoodoo.conf and find the line under [DirectX] called VRAM and change the value from 2048 to 4096. Line 194

You can choose to use a better form of anti-aliasing than the one in-game then you can use dgvoodoo instead. Edit the dgVoodoo.conf and find the line under [DirectX] called Antialiasing
and change it from "off" to either 2x, 4x, or 8x. Line 199
