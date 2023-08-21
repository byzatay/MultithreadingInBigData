import pandas as pd
import nltk
from nltk.corpus import stopwords


# Noktalama işaretlerini silme fonksiyonu
def clean(value):
    val = value
    if type(value) != str:
        val = str(value)

    if "," in val:
        val = val.replace(",", " ")
    elif "." in val:
        val = val.replace(".", " ")
    elif ";" in val:
        val = val.replace(";", " ")
    elif ":" in val:
        val = val.replace(":", " ")
    elif "?" in val:
        val = val.replace("?", " ")
    elif "!" in val:
        val = val.replace("!", " ")
    elif "-" in val:
        val = val.replace("-", " ")
    elif "\'" in val:
        val = val.replace("\'", "")
    elif "&" in val:
        val = val.replace("&", " ")
    return val

# Tablonun okunması
table = pd.read_csv("complaints.csv", low_memory=False)

# Gereksiz sütunların silinmesi
table.drop('Date received', axis=1, inplace=True)
table.drop('Sub-product', axis=1, inplace=True)
table.drop('Sub-issue', axis=1, inplace=True)
table.drop('Consumer complaint narrative', axis=1, inplace=True)
table.drop('Company public response', axis=1, inplace=True)
table.drop('Tags', axis=1, inplace=True)
table.drop('Consumer consent provided?', axis=1, inplace=True)
table.drop('Submitted via', axis=1, inplace=True)
table.drop('Date sent to company', axis=1, inplace=True)
table.drop('Company response to consumer', axis=1, inplace=True)
table.drop('Timely response?', axis=1, inplace=True)
table.drop('Consumer disputed?', axis=1, inplace=True)

# Tablodaki null değerlerin silinmesi
table = table.dropna()

# Noktalama işaretlerini silme
table["Product"] = table["Product"].apply(clean)
table["Issue"] = table["Issue"].apply(clean)
table["Company"] = table["Company"].apply(clean)
table["Company"] = table["Company"].apply(clean)
table["State"] = table["State"].apply(clean)
table["ZIP code"] = table["ZIP code"].apply(clean)
table["Complaint ID"] = table["Complaint ID"].apply(clean)

# Stopword lerin oluşturulması
nltk.download('stopwords')

stop = stopwords.words('english')
stop.append("can't")
stop.append("can")
stop.append("t")
stop.append(" ")

# Stopword lerin kaldırılması
table["Product"] = table["Product"].apply(lambda x: ' '.join([word for word in x.split() if word not in (stop)]))
table["Issue"] = table["Issue"].apply(lambda x: ' '.join([word for word in x.split() if word not in (stop)]))
table["Company"] = table["Company"].apply(lambda x: ' '.join([word for word in x.split() if word not in (stop)]))
table["State"] = table["State"].apply(lambda x: ' '.join([word for word in x.split() if word not in (stop)]))

# Temizlenmiş datanın csv ye atılması
table.to_csv("new.csv")
