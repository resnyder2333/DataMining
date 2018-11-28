# -*- coding: utf-8 -*-
"""
Created on Mon Nov 26 04:04:40 2018

Program to use SVM to determine spam and non-spam emails

@author: Daniel
"""
#imports
import os
import numpy as np
from sklearn import svm
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics import confusion_matrix
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
    
    #SVM, this is the learning part of the code
    clf = svm.SVC(kernel='linear', C = 1.0)
    
    #train & test
    # clf.fit(train_data, train_labels) trains based on the data
    # .predict(test_data) gets the trained model
    # to guess based on the matrices, y_pred holds the guesses
    y_pred = clf.fit(train_data, train_labels).predict(test_data)
    
    #Process results:
    #Confusion matrix compares the guesses to the true values, stores in:
    #  TN FN
    #  FP TP
    conf_matrix = confusion_matrix(test_labels, y_pred)
    # Accuracy = (TP + FP) / TOTAL
    accuracy = (conf_matrix[0][0] + conf_matrix[1][1]) / len(test_labels)
    # Precision = TP / Guessed Positives
    Precision = conf_matrix[1][1] / (conf_matrix[1][0] + conf_matrix[1][1])
    #Output results
    print("confusion matrix:")
    print(conf_matrix)
    
    print("Accuracy:")
    print(accuracy)
    
    print("Precision:")
    print(Precision)
    

# Boilerplate.
if __name__ == "__main__":
  main()    