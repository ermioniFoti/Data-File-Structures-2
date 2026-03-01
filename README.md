# 2D Spatial Data Structures: k-d Tree & PR Quadtree

A comprehensive implementation and performance analysis of two advanced spatial data structures designed to handle two-dimensional geometric points. This project was developed as the **2nd Assignment** for the **Data Structures and Files** course at the **Technical University of Crete** (Spring 2023).

## 📌 Project Overview
The objective of this assignment is to understand and implement structures that extend Binary Search Trees (BST) into multi-dimensional space. The project focuses on storing points situated in an $N \times N$ grid and efficiently performing insertion and search operations.

## 🚀 Implemented Data Structures

### 1. k-d Tree (2-Dimensional)
A binary tree adaptation where the discriminator (the key used for comparison) alternates at each level.
* **Structure:**
    * **Root (Level 0):** Compares based on the **x-coordinate**.
    * **Level 1:** Compares based on the **y-coordinate**.
    * **Level 2:** Reverts to **x**, and so on.
* **Insertion Logic:** A new point is compared against the current node. If the value is strictly less (<), the traversal moves left; otherwise (>=), it moves right.
* **Search Logic:** Follows the same alternating path logic until the target node is found or a NULL reference is reached.

### 2. PR Quadtree (Point Region Quadtree)
A tree structure where each internal node has exactly four children, representing the division of a 2D region into four equal quadrants (NW, NE, SW, SE).
* **Structure:** The root represents the entire $N \times N$ area. Each split divides the dimensions by 2.
* **Leaf Capacity:** Points are stored **only in leaf nodes**. Each leaf can hold **at most one point**.
* **Splitting Rule:** If a point is inserted into an already occupied leaf, the region is subdivided into 4 sub-quadrants. This process repeats recursively until the existing point and the new point fall into separate leaf regions.

## 📊 Performance Analysis & Experimentation
The project includes a benchmarking suite to evaluate the efficiency of both structures using the **Average Tree Depth** as the primary metric.

### Experimental Setup
* **Space Dimensions:** $N = 2^{16}$ ($65,536 \times 65,536$ grid).
* **Dataset Sizes (M):** Experiments are run for $M = 200, 500, 1000, 10000, 30000, 50000, 70000, 100000$ random points.
* **Operations:**
    * **Insertion:** $M$ random points are inserted.
    * **Search:** 100 successful searches (existing points) and 100 unsuccessful searches (non-existent points) are performed for each $M$.

### Metrics & Visualization
The output includes scatter charts plotting **Average Depth vs. Number of Elements (M)** for four distinct scenarios:
1.  **k-d Tree:** Successful Search.
2.  **k-d Tree:** Unsuccessful Search.
3.  **PR Quadtree:** Successful Search.
4.  **PR Quadtree:** Unsuccessful Search.

## 🛠️ Implementation Details
* **Code Standards:**
    * Parametric design allowing easy modification of $N$ and $M$.
    * Clean, structured code with explanatory comments.
    * Dynamic memory allocation used for node management.

## 📂 Deliverables
The repository structure includes:
* `src/`: Source code with separate classes for each tree structure.
* `report/`: A detailed PDF report containing the performance diagrams, complexity analysis, and justification of the results.

---
*Developed for the School of Electronic and Computer Engineering, Technical University of Crete.*
