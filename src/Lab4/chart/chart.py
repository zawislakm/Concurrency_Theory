from statistics import mean
import matplotlib.pyplot as plt
import csv

def stats():
    stats = []

    with open("data.csv", "r") as f:
        csvreader = csv.reader(f, delimiter=";")

        for row in csvreader:
            cleaned_row = [int(item) if item.isdigit() else item for item in row]
            cleaned_row.pop()
            stats.append(cleaned_row)

    name = "Wariant 3 wersja 2"
    philo = [i+1 for i in range(len(stats))]

    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12, 4) ) # Poprawione "subplots" z "subplot"
    plt.suptitle(f"{name}, N = {len(stats)}")

    # ax1.bar(philo, [mean(row) for row in stats])
    ax1.boxplot(stats,showfliers=False)
    ax1.set_yscale('log')
    ax1.set_title("Czas oczekiwania na widelce")
    ax1.set_xlabel('Filozofowie')
    ax1.set_ylabel('Czas [ns]')

    ax2.bar(philo, [len(row) for row in stats])
    ax2.set_title("Liczba podniesionych dwóch widelców")
    ax2.set_xlabel('Filozofowie')
    ax2.set_ylabel('Ilość podniesionych widelców')

    # ax1.set_xticks(philo)
    ax2.set_xticks(philo)

    plt.savefig(name + str(len(stats)))

def eat():
    out_eat = []
    with open("outeat.csv", "r") as f:
        csvreader = csv.reader(f, delimiter=";")
        for row in csvreader:
            cleaned_row = [int(item) if item.isdigit() else item for item in row]
            cleaned_row.pop()
            out_eat.append(cleaned_row[0])

    print(out_eat)
    philo = [i for i in range(len(out_eat))]
    plt.bar(philo, out_eat)  # Poprawione przekazanie pojedynczej listy
    plt.title("Liczba podniesionych widelców na zewnątrz jadalni")
    plt.xlabel("Filozofowie")
    plt.ylabel('Ilość podniesionych widelców')
    plt.xticks(philo)
    plt.savefig("outeat" + str(len(out_eat)))



# eat()
stats()
