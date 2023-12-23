import matplotlib.pyplot as plt
import networkx as nx
import queue
import random
import sys
from networkx.drawing.nx_agraph import graphviz_layout


def calculate_alphabet(edges: set) -> None:
    alphabet = set()
    for e1, e2 in edges:
        alphabet.add(e1)
        alphabet.add(e2)

    print("Found alphabet")
    print(alphabet)
    print("---------------------------------")


def calculate_graph(n: int) -> nx.DiGraph:
    G = nx.DiGraph()
    edges = set()

    for i in range(1, n):
        for k in range(i + 1, n + 1):
            for j in range(i, n + 2):
                edges.add((f"A{k}{i}", f"B{k}{j}{i}"))
                edges.add((f"B{k}{j}{i}", f"C{k}{j}{i}"))

    for i in range(1, n):
        for j in range(i + 2, n + 2):
            for k in range(i + 2, n + 1):
                edges.add((f"C{i + 1}{j}{i}", f"B{k}{j}{i + 1}"))

    for i in range(1, n - 1):
        for k in range(i + 2, n + 1):
            for j in range(i + 2, n + 2):
                edges.add((f"C{k}{j}{i}", f"C{k}{j}{i + 1}"))

    for i in range(1, n - 1):
        for k in range(i + 2, n + 1):
            edges.add((f"C{i + 1}{i}{i}", f"A{k}{i + 1}"))

        for k in range(i + 2, n + 1):
            edges.add((f"C{k}{i}{i}", f"A{k}{i + 1}"))

    calculate_alphabet(edges)

    print("Found dependency")
    print(edges)
    print("---------------------------------")

    G.add_edges_from(edges)

    return G


def calculate_fnf(G: nx.DiGraph) -> (int, dict):
    adj = {}
    visited_time = {}
    Q = queue.Queue()
    for v in G.nodes():
        adj[v] = set(G.neighbors(v))
        visited_time[v] = 0
        Q.put(v)

    while not Q.empty():
        u = Q.get()
        for v in adj.get(u, set()):
            visited_time[v] = visited_time.get(u, 0) + 1
            Q.put(v)

    foaty = {}
    for key, value in visited_time.items():
        vertices = foaty.get(value, [])
        vertices.append(key)
        foaty[value] = vertices

    foaty = dict(sorted(foaty.items(), key=lambda key: key[0]))

    print("Found Foaty: ")
    for key, vertices in foaty.items():
        print(key, vertices)
    print("------------------------------------")
    return len(foaty), visited_time


def random_color() -> (int, int, int, int):
    red = random.random()
    green = random.random()
    blue = random.random()

    return red, green, blue, 1


def draw_graph(G: nx.DiGraph) -> None:
    number_classes, fnf = calculate_fnf(G)
    try:
        pos = graphviz_layout(G, prog="dot")
    except ImportError:
        pos = nx.spring_layout(G)

    color_map = {i: random_color() for i in range(number_classes)}
    colors = [color_map[fnf.get(node)] for node in G.nodes]
    plt.figure(figsize=(14, 10))
    nx.draw(G, pos, with_labels=True, node_color=colors)
    plt.savefig("src/Lab8/Diekert.png")


def calculate(n: int) -> None:
    G = calculate_graph(n)
    draw_graph(G)


if __name__ == "__main__":
    try:
        size = int(sys.argv[1])
        calculate(size)
    except IndexError:
        print("No argument")
    except ValueError:
        print("Wrong argumnet format")
