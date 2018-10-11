from matplotlib import pyplot as plt

def plot_table(name, x_vals, y_vals):
    plt.ylabel("Frequency")
    plt.xlabel("Hash Position")
    plt.axis([0, len(x_vals)-1, 0, (1000000)//len(x_vals)])
    plt.plot(x_vals, y_vals, 'b', linewidth=0.5)
    plt.show()

def main():
    plot("hashVal_output")
    plot("myHash_output")
    plot("javaDefaultHash_output")

def plot(file_name):
    f = open(file_name)
    vals = list(f.readlines())
    x_vals = []
    y_vals = []
    for item in vals:
        [A, B] = list(map(int, item.split(' ')))
        x_vals.append(A)
        y_vals.append(B)
    plot_table(file_name, x_vals, y_vals)

if __name__ == '__main__':
    main()
