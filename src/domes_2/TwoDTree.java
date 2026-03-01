package domes_2;

public class TwoDTree {

	private class Node {
		boolean vertical;

		Point point;

		Node left, right;

		Node(Point p, boolean vertical) {
			this.point = p;
			this.vertical = vertical;
			this.left = this.right = null;
		}
	}

	private Node root;

	private SearchStats stats;

	public TwoDTree() {
		this.root = null;
		this.stats = new SearchStats();
	}

	public SearchStats getStats() {
		return stats;
	}

	private void _insert(Node t, Point p) {
		if ((t.vertical && p.x < t.point.x) || (!t.vertical && p.y < t.point.y)) {
			// turn left
			if (t.left != null) {
				_insert(t.left, p);
			} else {
				// new node here as our left child
				t.left = new Node(p, !t.vertical);
			}
		} else {
			// turn right
			if (t.right != null) {
				_insert(t.right, p);
			} else {
				// new node here as our right child
				t.right = new Node(p, !t.vertical);
			}
		}
	}

	public void insert(Point p) {
		if (root == null) {
			root = new Node(p, true);
		} else {
			_insert(root, p);
		}
	}

	private boolean _search(Node t, Point p, int depth) {
		if (t == null) {
			// Todo: add depth to sum for unsuccessful search
			stats.recordDepthForFailedSearch(depth);
			return false;
		}
		if (t.point.equals(p)) {
			// Todo: add depth to sum for successful search
			stats.recordDepthForSuccessfulSearch(depth);
			return true;
		}
		if (t.vertical) {
			if (p.x < t.point.x) {
				return _search(t.left, p, depth + 1);
			} else {
				return _search(t.right, p, depth + 1);
			}
		} else {
			if (p.y < t.point.y) {
				return _search(t.left, p, depth + 1);
			} else {
				return _search(t.right, p, depth + 1);
			}
		}
	}

	public boolean search(Point p) {
		if (root != null) {
			return _search(root, p, 0);
		}
		return false;
	}
}
