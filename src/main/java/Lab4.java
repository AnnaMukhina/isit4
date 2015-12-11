import java.util.*;

/**
 * @author anna_mukhina
 */
public class Lab4 {
    private final static Integer[][] RATING = new Integer[][]{
            {2, 4, 3, 5, 0, 0},
            {3, 4, 0, 0, 5, 1},
            {4, 0, 5, 5, 5, 0},
            {1, 4, 0, 4, 5, 4},
            {2, 0, 5, 5, 2, 0},
            {2, 4, 5, 5, 4, 0},
    };

    private static final int USER_ID = 0;

    public static void main(String[] args) {
        SortedMap<Double, Integer> usersCorrelations = new TreeMap<Double, Integer>();

        for (int i = 0; i < RATING.length; i++) {
            usersCorrelations.put(ubCollaborativeFiltering(RATING, USER_ID, i), i);
        }

        Integer bestMatchId = usersCorrelations.get(usersCorrelations.lastKey());

        ArrayList<Integer> recommendations = getRecommendations(RATING, USER_ID, bestMatchId);

        if(recommendations.size() == 0) {
            while(recommendations.size() == 0) {
                usersCorrelations.remove(usersCorrelations.lastKey());

                bestMatchId = usersCorrelations.get(usersCorrelations.lastKey());

                recommendations = getRecommendations(RATING, USER_ID, bestMatchId);
            }
        }

        System.out.println("Seems like you have similar tastes with user with id " + bestMatchId);
        System.out.println("Maybe you will like movies with numbers " + recommendations.toString());
    }

    private static double ubCollaborativeFiltering(Integer[][] rating, int id1, int id2) {
        if(id1 == id2) {
            return 0;
        }

        int count1 = 0;
        int count2 = 0;
        double totalRating1 = 0;
        double totalRating2 = 0;

        for (int i = 0; i < rating[id1].length; i++) {
            if (rating[id1][i] != 0) {
                count1++;
                totalRating1 += rating[id1][i];
            }
            if(rating[id2][i] != 0) {
                count2++;
                totalRating2 += rating[id2][i];
            }
        }

//        if (commonItems == 0) {
//            return 0;
//        }

        double averageRating1 = totalRating1 / count1;
        double averageRating2 = totalRating2 / count2;

        double numerator = 0;
        double brackets1 = 0;
        double brackets2 = 0;

        for (int i = 0; i < rating[id1].length; i++) {
            if (rating[id1][i] != 0 && rating[id2][i] != 0) {
                numerator += (rating[id1][i] - averageRating1)*(rating[id2][i] - averageRating2);

                brackets1 += Math.pow(rating[id1][i] - averageRating1, 2);
                brackets2 += Math.pow(rating[id2][i] - averageRating2, 2);
            }
        }
        return numerator / Math.sqrt(brackets1 * brackets2);
    }

    private static ArrayList<Integer> getRecommendations(Integer[][] rating, int id1, int id2) {
        ArrayList<Integer> recommendations = new ArrayList<Integer>();
        for(int i = 0; i < rating[id1].length; i++) {
            if(rating[id2][i] > 3 && rating[id1][i] == 0) {
                recommendations.add(i);
            }
        }
        return recommendations;
    }
}
