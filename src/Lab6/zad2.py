import queue

import matplotlib.pyplot as plt
import networkx as nx


class Vertex:

    def __init__(self, letter: str):
        self.letter: str = letter

    def __str__(self) -> str:
        return self.letter


class DependedVertex(Vertex):

    def __init__(self, operation: str, letter: str):
        super().__init__(letter=letter)
        self.left: str = ""
        self.right: set = set()
        self.decode_operation(operation)

    def decode_operation(self, operation: str) -> None:
        self.left = operation[0]
        for char in operation[1:]:
            if char.isalpha():
                self.right.add(char)

    def check_dependency(self, other_vertex) -> bool:
        return other_vertex.left in self.right


class Graph:

    def __init__(self):
        self.adjacency_graph: dict = {}


class DependedGraph(Graph):

    def __init__(self, operations: list):
        super().__init__()
        self.independent: set = set()
        self.dependent: set = set()
        self.graph_setup(operations)

    def graph_setup(self, operations: list) -> None:
        letters: list = []
        for i, operation in enumerate(operations):  # create vertices
            letter = chr(97 + i)
            self.adjacency_graph[DependedVertex(operation, letter)] = set()
            letters.append(letter)

        for vertex in self.adjacency_graph.keys():  # set graph edges
            for other_vertex in self.adjacency_graph.keys():
                if vertex != other_vertex and vertex.check_dependency(other_vertex):
                    self.adjacency_graph[vertex].add(other_vertex)
                    self.adjacency_graph[other_vertex].add(vertex)

        # set dependency and independency

        for letter1 in letters:
            for letter2 in letters:
                self.independent.add((letter1, letter2))
                self.independent.add((letter2, letter1))

        for x in self.adjacency_graph.keys():  # create Dependency
            self.dependent.add((x.letter, x.letter))
            for y in self.adjacency_graph.get(x, set()):
                self.dependent.add((x.letter, y.letter))
                self.dependent.add((y.letter, x.letter))

        self.independent -= self.dependent

        print(f"D = {self.dependent}")
        print(f"I = {self.independent}")

    def draw_graph(self) -> None:
        # draw graph
        graph = nx.Graph()
        plt.clf()

        for v1, v2 in self.dependent:
            if v1 != v2:
                graph.add_edge(v1, v2, color='black')

        for v1, v2 in self.independent:
            if v1 != v2:
                graph.add_edge(v1, v2, color='blue')

        edge_colors = [graph[u][v]['color'] for u, v in graph.edges()]

        pos = nx.spring_layout(graph)
        nx.draw(graph, pos, with_labels=True, edge_color=edge_colors, node_size=500, font_size=10, font_color='black')
        legend_labels = {"black": "dependent", "blue": "independent"}

        for color, label in legend_labels.items():
            plt.plot([], [], color=color, label=label)
        plt.legend()
        plt.savefig("dependedGraph.png")

    def get_letter_dependency(self) -> dict:  # create letter dependency to use in creating word graph
        letter_dependency: dict = {}
        for vertex, adj_list in self.adjacency_graph.items():
            letter_dependency[vertex.letter] = set()
            for other_vertex in adj_list:
                letter_dependency[vertex.letter].add(other_vertex.letter)
        return letter_dependency


class WordGraph(Graph):

    def __init__(self, word: str, adj: dict):
        super().__init__()
        self.word: str = word
        self.letter_dependency: dict = adj
        self.FNF: list = []
        self.reduced_graph: dict = {}
        self.graph_setup()

    def graph_setup(self) -> None:

        vertices: list = []
        for letter in self.word:  # create vertices
            vertex = Vertex(letter)
            vertices.append(vertex)
            self.adjacency_graph[vertex] = set()

        for i, vertex in enumerate(vertices):  # set graph edges
            for other_vertex in vertices[i + 1:]:
                if other_vertex.letter in self.letter_dependency[vertex.letter] or other_vertex.letter == vertex.letter:
                    self.adjacency_graph[vertex].add(other_vertex)

        # create Diekert graph
        self.transitive_reduction()

    def draw_graphs(self):
        # draw graph before reduction
        graph = nx.DiGraph(self.adjacency_graph)

        plt.clf()
        pos = nx.circular_layout(graph)
        nx.draw(graph, pos, with_labels=True)
        nx.draw_networkx(graph, pos, arrows=True)
        plt.savefig("BeforeHasseGraph.png")

        # draw graph after reduction
        new_graph = nx.DiGraph(self.reduced_graph)
        plt.clf()
        nx.draw(new_graph, pos, with_labels=True)
        nx.draw_networkx(new_graph, pos, arrows=True)
        plt.savefig("HasseGraph.png")

    def transitive_reduction(self):

        def dfs(start, current, visited, marked):
            visited[current] = True
            for neighbor in self.adjacency_graph.get(current, []):
                if not visited[neighbor]:
                    dfs(start, neighbor, visited, marked)
                marked[start][neighbor] |= marked[start][current]

        vertices = list(self.adjacency_graph.keys())
        num_vertices = len(vertices)

        # adjacency matrix and reachability matrix
        adjacency_matrix = [[0] * num_vertices for _ in range(num_vertices)]
        reachable_matrix = [[False] * num_vertices for _ in range(num_vertices)]

        for i in range(num_vertices):
            for neighbor in self.adjacency_graph.get(vertices[i], []):
                adjacency_matrix[i][vertices.index(neighbor)] = 1
                reachable_matrix[i][vertices.index(neighbor)] = True

        # DFS to set whether it's possible to reach vertex
        for i in range(num_vertices):
            visited = [False] * num_vertices
            dfs(i, i, visited, reachable_matrix)

        # remove redundant edges
        for i in range(num_vertices):
            for j in range(num_vertices):
                if adjacency_matrix[i][j] and reachable_matrix[i][j]:
                    for k in range(num_vertices):
                        if reachable_matrix[i][k] and reachable_matrix[k][j]:
                            reachable_matrix[i][j] = False

        # create reduced graph
        self.reduced_graph = {vertices[i]: set() for i in range(num_vertices)}
        for i in range(num_vertices):
            for j in range(num_vertices):
                if adjacency_matrix[i][j] and reachable_matrix[i][j]:
                    self.reduced_graph[vertices[i]].add(vertices[j])

    def find_FNF(self):
        # find fnf in reduced graph using BFS
        if self.reduced_graph == {}:
            return

        visited_time = {v: 0 for v in self.adjacency_graph.keys()}

        # BFS to set visited time
        Q = queue.Queue()
        for k in self.reduced_graph.keys():
            Q.put(k)

        while not Q.empty():
            u = Q.get()
            for v in self.reduced_graph.get(u, set()):
                visited_time[v] = visited_time.get(u, 0) + 1
                Q.put(v)

        self.FNF = ["" for _ in range(max(visited_time.values()) + 1)]
        for ver, char in zip(visited_time.values(), self.word):
            self.FNF[ver] += char
        fnf_format = ''.join([f'({elem})' for elem in self.FNF])
        print(f'Found FNF is {fnf_format}')


def solve(operations: list, word: str) -> None:
    dependency_graph = DependedGraph(operations)  # creates graph of dependency based on given operations
    dependency_graph.draw_graph()

    word_graph = WordGraph(word, dependency_graph.get_letter_dependency())
    # creates graph of word based of letter dependency and given word
    word_graph.draw_graphs()
    # find FNF of given word
    word_graph.find_FNF()


def zad2():
    operations = ["x<x+1", "y<y+2z", "x<3x+z", "w<w+v", "z<y-z", "v<x+v"]
    w = "acdcfbbe"
    solve(operations, w)
    # D = {('b', 'e'), ('a', 'b'), ('a', 'f'), ('c', 'e'), ('f', 'f'), ('a', 'c'), ('e', 'e'), ('a', 'a'), ('b', 'b'),
    #      ('f', 'c'), ('d', 'd'), ('f', 'a'), ('b', 'a'), ('c', 'f'), ('e', 'b'), ('d', 'f'), ('c', 'c'), ('c', 'a'),
    #      ('e', 'c'), ('f', 'd')}
    # I = {('b', 'd'), ('f', 'b'), ('d', 'e'), ('c', 'd'), ('e', 'd'), ('b', 'f'), ('b', 'c'), ('c', 'b'), ('a', 'e'),
    #      ('d', 'b'), ('a', 'd'), ('e', 'f'), ('f', 'e'), ('d', 'c'), ('d', 'a'), ('e', 'a')}
    # Found FNF is (ad)(cb)(cb)(fe)


def zad1():
    operations = ["x<x+y", "y<y+2z", "x<3x+z", "z<y-z"]
    w = "baadcb"
    solve(operations, w)
    # D = {('c', 'c'), ('d', 'b'), ('d', 'd'), ('a', 'a'), ('a', 'b'), ('b', 'b'), ('d', 'c'), ('b', 'a'), ('b', 'd'),
    #      ('c', 'a'), ('c', 'd'), ('a', 'c')}
    # I = {('d', 'a'), ('a', 'd'), ('b', 'c'), ('c', 'b')}
    # Found FNF is (b)(ad)(a)(cb)


zad1()
zad2()
