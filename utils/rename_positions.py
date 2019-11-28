import re
import os
import codecs

positions = {
    r"GK" : r"Goalkeeper",
    r"WB" : r"Wingback",
    r"LW" : r"Left Wing",
    r"RW" : r"Right Wing",
    r"LB" : r"Left Back",
    r"RB" : r"Right Back",
    r"CB" : r"Center Back",
    r"RCB": r"Right Center Back",
    r"LCB": r"Left Center Back",
    r"DM" : r"Defensive Midfield",
    r"RDM": r"Right Defensive Midfield",
    r"LDM": r"Left Defensive Midfield",
    r"CDM": r"Center Defensive Midfield", 
    r"CM" : r"Center Midfield",
    r"RCM": r"Right Center Midfield",
    r"LCM": r"Left Center Midfield",
    r"AM" : r"Attacking Midfield",
    r"LAM": r"Left Attacking Midfield",
    r"RAM": r"Right Attacking Midfield",
    r"CAM": r"Center Attacking Midfield",
    r"RM" : r"Right Midfield",
    r"LM" : r"Left Midfield",
    r"CF" : r"Center Forward",
    r"LF" : r"Left Forward",
    r"RF" : r"Right Forward",
    r"ST" : r"Striker",
    r"CF" : r"Striker",
    r"RS" : r"Right Striker",
    r"LS" : r"Left Striker"
}

prefix_find = r"(Position>)"
suffix_find = r"(</)"
prefix_replace = r"\1"
suffix_replace = r"\2"
# re.sub(r'(Position>)ST(</)', r'\1Striker\2', <CurrentNumber>15</CurrentNumber>)

fifa = "Documents/Uni/Master/1. Semester/Web Data Integration/WDI_2019/ExerciseIdentityResolution/data/FIFA19 target schema.xml"
tmp_fifa = "Documents/Uni/Master/1. Semester/Web Data Integration/WDI_2019/XML target schemas/FIFA19 target schema temp.xml"


with codecs.open(fifa, 'r', encoding='utf-16') as fi: 
    with codecs.open(tmp_fifa, 'w', encoding='utf-16') as fo:

        for line in fi:
            for k, v in positions.items():
                find = prefix_find + k + suffix_find
                replace = prefix_replace + v + suffix_replace
                line = re.sub(find, replace, line)
            fo.write(line)

os.remove(fifa) # remove original
os.rename(tmp_fifa, fifa) # rename temp to original name