package domes_2;

public class QuadTree {

	static abstract class Node {
		int x0, y0; // coordinates for N-W (top left) point

		int width;

		int height;

		final boolean isLeaf;

		Node(int x0, int y0, int width, int height, boolean isLeaf) {
			this.x0 = x0;
			this.y0 = y0;
			this.width = width;
			this.height = height;
			this.isLeaf = isLeaf;
		}
	}

	static class LeafNode extends Node {
		Point data;

		LeafNode(int x0, int y0, int width, int height, Point data) {
			super(x0, y0, width, height, true);
			this.data = data;
		}
	}

	static class InternalNode extends Node {
		Node nwTree, swTree, neTree, seTree;

		InternalNode(int x0, int y0, int width, int height) {
			super(x0, y0, width, height, false);
			final int w1 = width / 2, h1 = height / 2;
			this.nwTree = new LeafNode(x0, y0, w1, h1, null);
			this.neTree = new LeafNode(x0 + w1, y0, width - w1, h1, null);
			this.swTree = new LeafNode(x0, y0 + h1, w1, height - h1, null);
			this.seTree = new LeafNode(x0 + w1, y0 + h1, width - w1, height - h1, null);
		}
	}

	final int N;

	Node root;

	SearchStats stats;

	public QuadTree(int N) {
		this.N = N;
		this.root = new LeafNode(0, 0, N, N, null);
		this.stats = new SearchStats();
	}

	public SearchStats getStats() {
		return stats;
	}

	public void insert(Point data) {
		root = _insert(root, data);
	}

	public boolean search(Point data) {
		Node t = root;
		int depth = 0;
		// Walk internal nodes until we reach a leaf
		while (!t.isLeaf) {
			// Decide which direction to follow
			InternalNode q = (InternalNode) t;
			if (data.y < q.swTree.y0) { // N
				if (data.x < q.neTree.x0) { // N-W
					t = q.nwTree;
				} else { // N-E
					t = q.neTree;
				}
			} else { // S
				if (data.x < q.neTree.x0) { // S-W
					t = q.swTree;
				} else { // S-E
					t = q.seTree;
				}
			}
			++depth;
		}

		LeafNode r = (LeafNode) t;
		boolean found = data.equals(r.data);

		if (!found) {
			stats.recordDepthForFailedSearch(depth);
		} else {
			stats.recordDepthForSuccessfulSearch(depth);
		}
		return found;
	}

	//
	// Private helpers
	//

	private Node _insert(Node t, Point data) {
		if (!t.isLeaf) {
			_insert1((InternalNode) t, data);
			return t; // not changed
		}

		// Leaf
		final LeafNode r = (LeafNode) t;
		if (r.data == null) {
			r.data = data; // fill empty leaf
			return t;
		} else if (r.data.equals(data)) {
			return t; // duplicate (ignore and return)
		} else {
			// split in four
			InternalNode q = new InternalNode(r.x0, r.y0, r.width, r.height);
			_insert1(q, r.data); // re-insert existing data
			_insert1(q, data); // insert new data
			return q; // subtree has changed to q
		}
	}

	private void _insert1(InternalNode t, Point data) {
		if (data.y < t.swTree.y0) { // N
			if (data.x < t.neTree.x0) { // N-W
				t.nwTree = _insert(t.nwTree, data);
			} else { // N-E
				t.neTree = _insert(t.neTree, data);
			}
		} else { // S
			if (data.x < t.neTree.x0) { // S-W
				t.swTree = _insert(t.swTree, data);
			} else { // S-E
				t.seTree = _insert(t.seTree, data);
			}
		}
	}
}
