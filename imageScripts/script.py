
from PIL import Image
import os


# 36x36 (0.75x) for low-density
# 48x48 (1.0x baseline) for medium-density
# 72x72 (1.5x) for high-density
# 96x96 (2.0x) for extra-high-density
# 180x180 (3.0x) for extra-extra-high-density
# 192x192 (4.0x) for extra-extra-extra-high-density (launcher icon only; see note above)

names = ["mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi", "large-mdpi", "xlarge-mdpi"]

sizes = {
	"mdpi" : (48, 48),
	"hdpi" : (72, 72),
	"xhdpi" : (96, 96),
	"xxhdpi" : (144, 144),
	"xxxhdpi" : (192, 192),
	# Additional for Compatibility on Tablets
	"large-mdpi" : (96, 96),
	"xlarge-mdpi" : (144, 144),
}

dpi_values = {
	"mdpi" : 160,
	"hdpi" : 240,
	"xhdpi" : 320,
	"xxhdpi" : 480,
	"xxxhdpi" : 640,
	# Additional for Compatibility on Tablets
	"large-mdpi" : 320,
	"xlarge-mdpi" : 480,
}

basedir = "../app/src/main/res/drawable"
dirname = "img"

def resizeDrawables(filename):

	fullpath = dirname + "/" + filename
	im = Image.open(fullpath)

	print  "Original Image DPI : " 
	# print im.info["dpi"]

	for name in names:
		size = sizes[name]

		im_resized = im.resize(size, Image.ANTIALIAS)
		newfilename = basedir +"-" + name + "/" + filename
		# newfilename = "to-send" + "/" + name + "-" + filename
		print newfilename 
		# print im_resized.info["dpi"]
		im_resized.save(newfilename)
	

for filename in os.listdir('img'):
	resizeDrawables(filename)