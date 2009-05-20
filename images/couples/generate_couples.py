#!/usr/bin/env python

import os
from PIL import Image

student_types = ["band", "gangster", "goth", "science", "sport"] # add rocker when female graphic is available

for female in student_types:
	for male in student_types:
		filename = "%s_%s.png" % (female, male)
		
		print filename
		
		if os.path.exists(filename):
			print "File already exists!"
			continue
		
		# get size for female
		female_im = Image.open("../student/%s_f.png" % female)
		female_w, female_h = female_im.size
		
		# get size for male
		male_im = Image.open("../student/%s_m.png" % male)
		male_w, male_h = male_im.size
		
		if male_h > female_h:
			h = male_h
		else:
			h = female_h
		
		im = Image.new("RGBA", (female_w + male_w, h))
		
		im.paste(female_im, (0, h - female_h))
		im.paste(male_im, (female_w, h - male_h))
		
		im.save(filename)
