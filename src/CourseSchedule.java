import java.util.*;

/*
LeetCode 207. Course Schedule

Return true if you can finish all courses (i.e., no cycle in the graph).

Approach: Topological Sort
- Build a directed graph (adjacency list) from prerequisite -> dependent.
- Track indegree of each course (how many prerequisites it has).
- Start with all courses that have indegree 0 (no prerequisites).
- Repeatedly take a course, reduce indegree of its dependents.
- If we can process all courses => no cycle => true, else false.

Time Complexity: O(V + E)
  V = numCourses
  E = prerequisites.length

Space Complexity: O(V + E)
  adjacency list + indegree array + queue
*/
public class CourseSchedule {

    public boolean canFinish(int numCourses, int[][] prerequisites) {

        // indegrees[i] = number of prerequisites needed before taking course i
        int[] indegrees = new int[numCourses];

        // adjacency list: for each course, list the courses that depend on it
        // map.get(b) contains all 'a' such that b -> a
        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Build graph and indegree array
        for (int[] pr : prerequisites) {
            int dependent = pr[0];     // course that depends on another
            int prerequisite = pr[1];  // course needed first

            indegrees[dependent]++;

            graph.putIfAbsent(prerequisite, new ArrayList<>());
            graph.get(prerequisite).add(dependent);
        }

        // Add all courses with indegree 0 into queue (can be taken immediately)
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegrees[i] == 0) {
                q.add(i);
            }
        }

        // Count how many courses we are able to process/take
        int processed = 0;

        // Process courses in BFS order
        while (!q.isEmpty()) {
            int course = q.poll();
            processed++; // we "take" this course

            // For all courses that depend on this course, reduce their indegree
            List<Integer> dependents = graph.get(course);
            if (dependents != null) {
                for (int next : dependents) {
                    indegrees[next]--;
                    if (indegrees[next] == 0) {
                        q.add(next); // now available to take
                    }
                }
            }
        }

        // If we processed all courses, no cycle exists
        return processed == numCourses;
    }

    // ---------------- Simple main method to test (no assertions) ----------------
    public static void main(String[] args) {
        CourseSchedule cs = new CourseSchedule();

        // Example 1:
        // numCourses = 2, prerequisites = [[1,0]]
        // 0 -> 1, can finish
        int numCourses1 = 2;
        int[][] prerequisites1 = { {1, 0} };
        System.out.println("Test 1");
        System.out.println("numCourses = " + numCourses1 + ", prerequisites = [[1,0]]");
        System.out.println("canFinish  = " + cs.canFinish(numCourses1, prerequisites1));
        System.out.println();

        // Example 2:
        // numCourses = 2, prerequisites = [[1,0],[0,1]]
        // cycle, cannot finish
        int numCourses2 = 2;
        int[][] prerequisites2 = { {1, 0}, {0, 1} };
        System.out.println("Test 2");
        System.out.println("numCourses = " + numCourses2 + ", prerequisites = [[1,0],[0,1]]");
        System.out.println("canFinish  = " + cs.canFinish(numCourses2, prerequisites2));
        System.out.println();

        // Example 3:
        // numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
        // 0 -> 1, 0 -> 2, 1 -> 3, 2 -> 3, can finish
        int numCourses3 = 4;
        int[][] prerequisites3 = { {1, 0}, {2, 0}, {3, 1}, {3, 2} };
        System.out.println("Test 3");
        System.out.println("numCourses = " + numCourses3 + ", prerequisites = [[1,0],[2,0],[3,1],[3,2]]");
        System.out.println("canFinish  = " + cs.canFinish(numCourses3, prerequisites3));
    }
}
