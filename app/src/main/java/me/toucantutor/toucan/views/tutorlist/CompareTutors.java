package me.toucantutor.toucan.views.tutorlist;

import java.util.Comparator;

/**
 * Created by aadil on 2/22/15.
 */
public class CompareTutors {


    public static final Comparator<Tutor> NAME = new Comparator<Tutor>() {
        @Override
        public int compare(Tutor lhs, Tutor rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            } else if (lhs == null || lhs.getName() == null) {
                return 1;
            } else if (rhs == null || rhs.getName() == null) {
                return -1;
            } else {
                return lhs.getName().compareTo(rhs.getName());
            }
        }
    };

    public static final Comparator<Tutor> DISTANCE = new Comparator<Tutor>() {
        @Override
        public int compare(Tutor lhs, Tutor rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            } else if (lhs == null) {
                return 1;
            } else if (rhs == null) {
                return -1;
            } else {
                return lhs.getDistance().compareTo(rhs.getDistance());
            }
        }
    };

    public static final Comparator<Tutor> RATING = new Comparator<Tutor>() {
        @Override
        public int compare(Tutor lhs, Tutor rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            } else if (lhs == null || lhs.getRating() == 0) {
                return -1;
            } else if (rhs == null || rhs.getRating() == 0) {
                return 1;
            } else {
                return rhs.getRating().compareTo(lhs.getRating());
            }
        }
    };

    public static final Comparator<Tutor> PRICE = new
            Comparator<Tutor>() {
                @Override
                public int compare(Tutor lhs, Tutor rhs) {
                    if (lhs == null && rhs == null) {
                        return 0;
                    } else if (lhs == null || lhs.getPrice() == 0) {
                        return 1;
                    } else if (rhs == null || rhs.getPrice() == 0) {
                        return -1;
                    } else {
                        return lhs.getPrice().compareTo(rhs.getPrice());
                    }
                }
            };



}