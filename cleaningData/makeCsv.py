import pandas as pd
import csv

# Temizlenmis verinin okunması
cleanTable = pd.read_csv("new.csv", low_memory=False)

# Oluşan fazladan sütunun silinmesi
cleanTable.drop('Unnamed: 0', axis=1, inplace=True)

# Sütunların listelere atılması
product = cleanTable["Product"]
issue = cleanTable["Issue"]
company = cleanTable["Company"]
state = cleanTable["State"]
zipcode = cleanTable["ZIP code"]

# Temizlenmiş datanın csv ye atılması
file_product = open('product.csv', 'w+', newline='')
file_issue = open('issue.csv', 'w+', newline='')
file_company = open('company.csv', 'w+', newline='')
file_state = open('state.csv', 'w+', newline='')
file_zipcode = open('zipcode.csv', 'w+', newline='')

# writing the data into the file
with file_product:
    write = csv.writer(file_product)
    write.writerows(product)
with file_issue:
    write = csv.writer(file_issue)
    write.writerows(issue)
with file_company:
    write = csv.writer(file_company)
    write.writerows(company)
with file_state:
    write = csv.writer(file_state)
    write.writerows(state)
with file_zipcode:
    write = csv.writer(file_zipcode)
    write.writerows(zipcode)

