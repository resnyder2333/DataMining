import os
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import classification_report, confusion_matrix  
from sklearn.feature_extraction.text import CountVectorizer

#Get data from files in selected folders
def GetData(Folders):

    corpus = []
    labels = [] #true class values where 0 is spam and 1 is not
    for Folder in Folders:
        for file in os.listdir(Folder):
            text = ""
            
            #Determine class
            if "spm" in file:
                labels.append(0)
            else:
                labels.append(1)
            try:
                with open(Folder + "\\" + file,'r') as f:
                    for line in f:
                        #Make into one big text block
                        text += " " + line
                        
        
            except Exception as e:
                raise e
                print ("No files found here!")
            corpus.append(text)

    return corpus,labels

#Method for splitting lists, like the labels
def split_list(a_list):
    half = len(a_list)//2
    return a_list[:half], a_list[half:]

#Method for splitting the sparse matrix, like the features matrix
def split_sparse_list(a_list):
    half = (a_list.shape[0])//2
    return a_list[:half], a_list[half:]

def main():
    location = os.getcwd() # get present working directory location here
    #Folders to get data in
    trainFolder = location + "\\train"
    testFolder = location + "\\test"
    #Get data from all files in folders
    corpus, labels = GetData([trainFolder, testFolder])
    
    #Vectorizer, creates a sparse matrix
    #of all words in each file and their frequencies
    vectorizer = CountVectorizer()

    #fuse datapoints into 1 array of rows filled with data
    features = vectorizer.fit_transform(corpus)

    #Split the data and labels into training and testing sets
    train_data, test_data = split_sparse_list(features)
    train_labels, test_labels = split_list(labels)
    
    x_train = train_data
    y_train = train_labels
    x_test = test_data
    y_test = test_labels
    
    classifier = DecisionTreeClassifier()  
    classifier = classifier.fit(x_train, y_train)  
    
    y_pred = classifier.predict(x_test)
    
    print(confusion_matrix(y_test, y_pred))  
    print(classification_report(y_test, y_pred))  
    
    # Boilerplate.
if __name__ == "__main__":
  main()    
    