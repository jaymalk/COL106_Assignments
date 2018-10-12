#include <fstream>
#include <vector>
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

string print_line(int max, int cols) {
    string s = "+";
    for(int i=0; i<cols; i++) {
        for(int i=0; i<max+2; i++)
            s = s+"-";
        s+="+";
    }
    return s;
}

void prettyprint(string inputfilename, string outputfilename) {
    ifstream in;
    vector<vector<string> > parent_vector;
    in.open(inputfilename);
    string line;
    int max = 0, cols;
    while(getline(in, line)) {
        istringstream words(line);
        string word;
        vector<string> column_vector;
        while(words >> word) {
            if(word.length()>max)
                max = word.length();
            column_vector.push_back(word);
        }
        parent_vector.push_back(column_vector);
    }
    cols = parent_vector[0].size();
    ofstream out;
    out.open(outputfilename);
    for(int i=0; i<parent_vector.size(); i++) {
        out << print_line(max, cols) << "\n";
        vector<string> current_column = parent_vector[i];
        for(int j=0; j<cols; j++) {
            out << "| " << current_column[j];
            for(int k=0; k<max+1-current_column[j].length(); k++)
                out << " ";
        }
        out << "|\n";
    }
    out << print_line(max, cols);
}

int main() {
    prettyprint("input_q2", "output_q2");
    return 0;
}
