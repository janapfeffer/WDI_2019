import urllib.request
from bs4 import BeautifulSoup
import pandas as pd
import re

# create all the possible urls with data
shared_url_part = 'https://raw.githubusercontent.com/ewenme/transfers/master/data/'
years = range(2012, 2020)
leagues = ['dutch_eredivisie', 'english_championship', 'english_premier_league',
           'french_ligue_1', 'german_bundesliga_1', 'italian_serie_a', 'portugese_liga_nos',
           'russian_premier_liga', 'spanish_primera_division']

urls = [shared_url_part + str(year) + '/' + league + '.csv' for year in years for league in leagues]


# create empty dataframe
transfers = pd.DataFrame()

for url in urls:
    try:
        page = urllib.request.urlopen(url)
    except:
        pass
    soup = BeautifulSoup(page)
    data_string = str(soup).replace('<html><body><p>', '').replace('</p></body></html>', '')
    data_string = re.sub(r'(End of loan\w{3}\s\d\d*)(,)(\s\d{4})', r'\1.\3', data_string)
    rows = data_string.splitlines()
    df = pd.DataFrame([row.split(",") for row in rows])
    df.columns = df.iloc[0]
    df = df[1:]
    if transfers.empty:
        transfers = df.copy()
    else:
        transfers = pd.concat([transfers, df])

transfers = transfers[transfers['year'] != 'Liga Nos']
transfers = transfers[['club_name', 'player_name', 'age', 'position', 'club_involved_name',
       'fee', 'transfer_movement', 'fee_cleaned', 'league_name', 'year',
       'season']]

transfers.to_csv('data/transfers.csv', index = False)
