import csv
import matplotlib.pyplot as plt


def stats():
    stats = []

    with open("data.csv", "r") as f:
        csvreader = csv.reader(f, delimiter=";")

        for row in csvreader:
            cleaned_row = [int(item) if item.isdigit() else item for item in row]
            cleaned_row.pop()
            stats.append(cleaned_row)

    name = "Wariant 6 wersja 2"
    philo = [i + 1 for i in range(len(stats))]

    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12, 4))  # Poprawione "subplots" z "subplot"
    plt.suptitle(f"{name}, N = {len(stats)}")

    # ax1.bar(philo, [mean(row) for row in stats])
    ax1.boxplot(stats, showfliers=False)
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


stats()
