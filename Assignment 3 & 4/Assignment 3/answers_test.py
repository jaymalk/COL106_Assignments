
def main():
    f = open("my_answers.txt")
    ans = list(f.readlines())
    print(ans)
    f = open("answers.txt")
    ans2 = list(f.readlines())
    print(ans2)
    for i in range(len(ans)):
        print(ans[i] == ans2[i])

if __name__ == '__main__':
    main()