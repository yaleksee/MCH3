package ch.controller;

import ch.model.Field;

import java.util.ArrayList;

public class Controller {

    public Controller(Field[][] fieldArray) {
        this.fieldArray = fieldArray;
        PinkMove = true;
    }

    private Field[][] fieldArray;
    private boolean PinkMove;
    private boolean PinkTake;
    private int xPink;
    private int yPink;
    private boolean GrayTake;
    private int xGray;
    private int yGray;
    private int countGrayCheckers = 12;
    private int countPinkCheckers = 12;

    /**
     *
     *
     *
     */

    public void clickController(int x, int y) {
        int pinkTotal = getCountPinkCheckers();
        int grayTotal = getCountGrayCheckers();
        if (pinkTotal != 0 && grayTotal != 0) {
            if (PinkMove) {
                if (PinkTake) {
                    if (availablePink(x, y)) {
                        xPink = x;
                        yPink = y;
                    } else {
                        if (availableFieldPink(x, y)) {
                            if (eatingPink(x, y)) {
                                movePink(x, y);
                                if (continueEatingPink()) {
                                    return;
                                } else {
                                    PinkTake = false;
                                    PinkMove = false;
                                }
                            } else {
                                movePink(x, y);
                                PinkTake = false;
                                PinkMove = false;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    if (availablePink(x, y)) {
                        PinkTake = true;
                        xPink = x;
                        yPink = y;
                    }
                }
            } else {
                if (GrayTake) {
                    if (availableGray(x, y)) {
                        xGray = x;
                        yGray = y;
                    } else {
                        if (availableFieldGray(x, y)) {
                            if (eatingGray(x, y)) {
                                moveGray(x, y);
                                if (continueEatingGray()) {
                                    return;
                                } else {
                                    GrayTake = false;
                                    PinkMove = true;
                                }
                            } else {
                                moveGray(x, y);
                                GrayTake = false;
                                PinkMove = true;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    if (availableGray(x, y)) {
                        GrayTake = true;
                        xGray = x;
                        yGray = y;
                    }
                }
            }
        }
        if (pinkTotal == 0) {
            System.out.println("Серые выиграли");
        }
        if (grayTotal == 0) {
            System.out.println("Розовые выиграли");
        }
    }

    /**
     *
     *
     *
     */


    private boolean availablePink(int x, int y) {
        ArrayList<Pair> eating = new ArrayList<Pair>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fieldArray[i][j].Pink) {
                    if ((i - 1) >= 0 && (j - 1) >= 0) {
                        if (fieldArray[i - 1][j - 1].Gray || fieldArray[i - 1][j - 1].GrayQueen) {
                            if ((i - 2) >= 0 && (j - 2) >= 0) {
                                if (fieldArray[i - 2][j - 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                    if ((i - 1) >= 0 && (j + 1) < 8) {
                        if (fieldArray[i - 1][j + 1].Gray || fieldArray[i - 1][j + 1].GrayQueen) {
                            if ((i - 2) >= 0 && (j + 2) < 8) {
                                if (fieldArray[i - 2][j + 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                }
                if (fieldArray[i][j].PinkQueen) {
                    if ((i - 1) >= 0 && (j - 1) >= 0 && (fieldArray[i - 1][j - 1].Gray || fieldArray[i - 1][j - 1].GrayQueen)) {
                        if ((i - 2) >= 0 && (j - 2) >= 0 && fieldArray[i - 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    if ((i - 1) >= 0 && (j + 1) < 8 && (fieldArray[i - 1][j + 1].Gray || fieldArray[i - 1][j + 1].GrayQueen)) {
                        if ((i - 2) >= 0 && (j + 2) < 8 && fieldArray[i - 2][j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    if ((i + 1) < 8 && (j - 1) >= 0 && (fieldArray[i + 1][j - 1].Gray || fieldArray[i + 1][j - 1].GrayQueen)) {
                        if ((i + 2) < 8 && (j - 2) >= 0 && fieldArray[i + 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    if ((i + 1) < 8 && (j + 1) < 8 && (fieldArray[i + 1][j + 1].Gray || fieldArray[i + 1][j + 1].GrayQueen)) {
                        if ((i + 2) < 8 && (j + 2) < 8 && fieldArray[i + 2][j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                }
            }
        }

        if (eating.size() > 0) {
            if (forVoid(x, y, eating)) return true;
        } else if (fieldArray[x][y].Pink && (x - 1) >= 0 && (y - 1) >= 0 && fieldArray[x - 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].Pink && (x - 1) >= 0 && (y + 1) < 8 && fieldArray[x - 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].PinkQueen && (x - 1) >= 0 && (y - 1) >= 0 && fieldArray[x - 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].PinkQueen && (x - 1) >= 0 && (y + 1) < 8 && fieldArray[x - 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].PinkQueen && (x + 1) < 8 && (y - 1) >= 0 && fieldArray[x + 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].PinkQueen && (x + 1) < 8 && (y + 1) < 8 && fieldArray[x + 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        return false;
    }

    private boolean forVoid(int x, int y, ArrayList<Pair> eating) {
        for (int i = 0; i < eating.size(); i++) {
            if (eating.get(i).x == x && eating.get(i).y == y) {
                clearCurrent();
                fieldArray[x][y].Current = true;
                return true;
            }
        }
        return false;
    }

    private boolean availableFieldPink(int x, int y) {
        boolean key = false;
        if ((fieldArray[xPink][yPink].PinkQueen || fieldArray[xPink][yPink].Pink) && (xPink - 2) >= 0 && (yPink - 2) >= 0 && fieldArray[xPink - 2][yPink - 2].Empty && (xPink - 1) >= 0 && (yPink - 1) >= 0 && (fieldArray[xPink - 1][yPink - 1].Gray || fieldArray[xPink - 1][yPink - 1].GrayQueen)) {
            key = true;
            if (x == (xPink - 2) && y == (yPink - 2)) return true;
        }
        if ((fieldArray[xPink][yPink].PinkQueen || fieldArray[xPink][yPink].Pink) && (xPink - 2) >= 0 && (yPink + 2) < 8 && fieldArray[xPink - 2][yPink + 2].Empty && (xPink - 1) >= 0 && (yPink + 1) < 8 && (fieldArray[xPink - 1][yPink + 1].Gray || fieldArray[xPink - 1][yPink + 1].GrayQueen)) {
            key = true;
            if (x == (xPink - 2) && y == (yPink + 2)) return true;
        }
        if (fieldArray[xPink][yPink].PinkQueen && (xPink + 2) < 8 && (yPink - 2) >= 0 && fieldArray[xPink + 2][yPink - 2].Empty && (xPink + 1) < 8 && (yPink - 1) >= 0 && (fieldArray[xPink + 1][yPink - 1].Gray || fieldArray[xPink + 1][yPink - 1].GrayQueen)) {
            key = true;
            if (x == (xPink + 2) && y == (yPink - 2)) return true;
        }
        if (fieldArray[xPink][yPink].PinkQueen && (xPink + 2) < 8 && (yPink + 2) < 8 && fieldArray[xPink + 2][yPink + 2].Empty && (xPink + 1) < 8 && (yPink + 1) < 8 && (fieldArray[xPink + 1][yPink + 1].Gray || fieldArray[xPink + 1][yPink + 1].GrayQueen)) {
            key = true;
            if (x == (xPink + 2) && y == (yPink + 2)) return true;
        }
        if (!key) {
            if ((fieldArray[x][y].Empty && x == (xPink - 1) && y == (yPink - 1)) || (fieldArray[x][y].Empty && x == (xPink - 1) && y == (yPink + 1))) {
                return true;
            }
            if ((fieldArray[xPink][yPink].PinkQueen && fieldArray[x][y].Empty && x == (xPink - 1) && y == (yPink - 1)) || (fieldArray[xPink][yPink].PinkQueen && fieldArray[x][y].Empty && x == (xPink - 1) && y == (yPink + 1)) || (fieldArray[xPink][yPink].PinkQueen && fieldArray[x][y].Empty && x == (xPink + 1) && y == (yPink + 1)) || (fieldArray[xPink][yPink].PinkQueen && fieldArray[x][y].Empty && x == (xPink + 1) && y == (yPink - 1))) {
                return true;
            }
        }
        return false;
    }

    private boolean eatingPink(int x, int y) {
        if (x == (xPink - 2) && y == (yPink - 2) && fieldArray[x][y].Empty && (fieldArray[xPink - 1][yPink - 1].Gray || fieldArray[xPink - 1][yPink - 1].GrayQueen)) {
            if (fieldArray[xPink - 1][yPink - 1].Gray) fieldArray[xPink - 1][yPink - 1].Gray = false;
            else fieldArray[xPink - 1][yPink - 1].GrayQueen = false;
            fieldArray[xPink - 1][yPink - 1].Empty = true;
            setCountGrayCheckers(getCountGrayCheckers());
            return true;
        }
        if (x == (xPink - 2) && y == (yPink + 2) && fieldArray[x][y].Empty && (fieldArray[xPink - 1][yPink + 1].Gray || fieldArray[xPink - 1][yPink + 1].GrayQueen)) {
            if (fieldArray[xPink - 1][yPink + 1].Gray) fieldArray[xPink - 1][yPink + 1].Gray = false;
            else fieldArray[xPink - 1][yPink + 1].GrayQueen = false;
            fieldArray[xPink - 1][yPink + 1].Empty = true;
            setCountGrayCheckers(getCountGrayCheckers());
            return true;
        }
        if (fieldArray[xPink][yPink].PinkQueen && x == (xPink + 2) && y == (yPink - 2) && fieldArray[x][y].Empty && (fieldArray[xPink + 1][yPink - 1].Gray || fieldArray[xPink + 1][yPink - 1].GrayQueen)) {
            if (fieldArray[xPink + 1][yPink - 1].Gray) fieldArray[xPink + 1][yPink - 1].Gray = false;
            else fieldArray[xPink + 1][yPink - 1].GrayQueen = false;
            fieldArray[xPink + 1][yPink - 1].Empty = true;
            setCountGrayCheckers(getCountGrayCheckers());
            return true;
        }
        if (fieldArray[xPink][yPink].PinkQueen && x == (xPink + 2) && y == (yPink + 2) && fieldArray[x][y].Empty && (fieldArray[xPink + 1][yPink + 1].Gray || fieldArray[xPink + 1][yPink + 1].GrayQueen)) {
            if (fieldArray[xPink + 1][yPink + 1].Gray) fieldArray[xPink + 1][yPink + 1].Gray = false;
            else fieldArray[xPink + 1][yPink + 1].GrayQueen = false;
            fieldArray[xPink + 1][yPink + 1].Empty = true;
            setCountGrayCheckers(getCountGrayCheckers());
            return true;
        }
        return false;
    }

    private void movePink(int x, int y) {

        if (fieldArray[xPink][yPink].Pink) {
            if (x == 0) {
                fieldArray[x][y].PinkQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xPink][yPink].Empty = true;
                fieldArray[xPink][yPink].Pink = false;
            } else {
                fieldArray[x][y].Pink = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xPink][yPink].Empty = true;
                fieldArray[xPink][yPink].Pink = false;
            }
        } else {
            fieldArray[x][y].PinkQueen = true;
            fieldArray[x][y].Empty = false;
            fieldArray[xPink][yPink].Empty = true;
            fieldArray[xPink][yPink].PinkQueen = false;
        }
        xPink = x;
        yPink = y;
    }

    private boolean continueEatingPink() {
        if ((xPink - 2) >= 0 && (yPink - 2) >= 0 && fieldArray[xPink - 2][yPink - 2].Empty && fieldArray[xPink - 1][yPink - 1].Gray) {
            return true;
        }
        if ((xPink - 2) >= 0 && (yPink + 2) < 8 && fieldArray[xPink - 2][yPink + 2].Empty && fieldArray[xPink - 1][yPink + 1].Gray) {
            return true;
        }
        if (fieldArray[xPink][yPink].PinkQueen && (xPink + 2) < 8 && (yPink - 2) >= 0 && fieldArray[xPink + 2][yPink - 2].Empty && (fieldArray[xPink + 1][yPink - 1].Gray || fieldArray[xPink + 1][yPink - 1].GrayQueen)) {
            return true;
        }
        if (fieldArray[xPink][yPink].PinkQueen && (xPink + 2) < 8 && (yPink + 2) < 8 && fieldArray[xPink + 2][yPink + 2].Empty && (fieldArray[xPink + 1][yPink + 1].Gray || fieldArray[xPink + 1][yPink + 1].GrayQueen)) {
            return true;
        }
        return false;
    }

    /**
     *
     *
     *
     */

    private boolean availableGray(int x, int y) {
        ArrayList<Pair> eating = new ArrayList<Pair>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fieldArray[i][j].Gray) {
                    if ((i + 1) < 8 && (j - 1) >= 0) {
                        if (fieldArray[i + 1][j - 1].Pink || fieldArray[i + 1][j - 1].PinkQueen) {
                            if ((i + 2) < 8 && (j - 2) >= 0) {
                                if (fieldArray[i + 2][j - 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                    if ((i + 1) < 8 && (j + 1) < 8) {
                        if (fieldArray[i + 1][j + 1].Pink || fieldArray[i + 1][j + 1].PinkQueen) {
                            if ((i + 2) < 8 && (j + 2) < 8) {
                                if (fieldArray[i + 2][j + 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                }
                if (fieldArray[i][j].GrayQueen) {
                    if ((i - 1) >= 0 && (j - 1) >= 0 && (fieldArray[i - 1][j - 1].Pink || fieldArray[i - 1][j - 1].PinkQueen)) {
                        if ((i - 2) >= 0 && (j - 2) >= 0 && fieldArray[i - 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    if ((i - 1) >= 0 && (j + 1) < 8 && (fieldArray[i - 1][j + 1].Pink || fieldArray[i - 1][j + 1].PinkQueen)) {
                        if ((i - 2) >= 0 && (j + 2) < 8 && fieldArray[i - 2][j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    if ((i + 1) < 8 && (j - 1) >= 0 && (fieldArray[i + 1][j - 1].Pink || fieldArray[i + 1][j - 1].PinkQueen)) {
                        if ((i + 2) < 8 && (j - 2) >= 0 && fieldArray[i + 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    if ((i + 1) < 8 && (j + 1) < 8 && (fieldArray[i + 1][j + 1].Pink || fieldArray[i + 1][j + 1].PinkQueen)) {
                        if ((i + 2) < 8 && (j + 2) < 8 && fieldArray[i + 2][j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                }
            }
        }

        if (eating.size() > 0) {
            if (forVoid(x, y, eating)) return true;
        } else if (fieldArray[x][y].Gray && (x + 1) < 8 && (y - 1) >= 0 && fieldArray[x + 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].Gray && (x + 1) < 8 && (y + 1) < 8 && fieldArray[x + 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].GrayQueen && (x - 1) >= 0 && (y - 1) >= 0 && fieldArray[x - 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].GrayQueen && (x - 1) >= 0 && (y + 1) < 8 && fieldArray[x - 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].GrayQueen && (x + 1) < 8 && (y - 1) >= 0 && fieldArray[x + 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        } else if (fieldArray[x][y].GrayQueen && (x + 1) < 8 && (y + 1) < 8 && fieldArray[x + 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        return false;
    }

    private boolean availableFieldGray(int x, int y) {
        boolean key = false;
        if ((fieldArray[xGray][yGray].GrayQueen || fieldArray[xGray][yGray].Gray) && (xGray + 2) < 8 && (yGray - 2) >= 0 && fieldArray[xGray + 2][yGray - 2].Empty && (xGray + 1) < 8 && (yGray - 1) >= 0 && (fieldArray[xGray + 1][yGray - 1].Pink || fieldArray[xGray + 1][yGray - 1].PinkQueen)) {
            key = true;
            if (x == (xGray + 2) && y == (yGray - 2)) return true;
        }
        if ((fieldArray[xGray][yGray].GrayQueen || fieldArray[xGray][yGray].Gray) && (xGray + 2) < 8 && (yGray + 2) < 8 && fieldArray[xGray + 2][yGray + 2].Empty && (xGray + 1) < 8 && (yGray + 1) < 8 && (fieldArray[xGray + 1][yGray + 1].Pink || fieldArray[xGray + 1][yGray + 1].PinkQueen)) {
            key = true;
            if (x == (xGray + 2) && y == (yGray + 2)) return true;
        }
        if (fieldArray[xGray][yGray].GrayQueen && (xGray - 2) >= 0 && (yGray - 2) >= 0 && fieldArray[xGray - 2][yGray - 2].Empty && (xGray - 1) >= 0 && (yGray - 1) >= 0 && (fieldArray[xGray - 1][yGray - 1].Pink || fieldArray[xGray - 1][yGray - 1].PinkQueen)) {
            key = true;
            if (x == (xGray - 2) && y == (yGray - 2)) return true;
        }
        if (fieldArray[xGray][yGray].GrayQueen && (xGray - 2) >= 0 && (yGray + 2) < 8 && fieldArray[xGray - 2][yGray + 2].Empty && (xGray - 1) >= 0 && (yGray + 1) < 8 && (fieldArray[xGray - 1][yGray + 1].Pink || fieldArray[xGray - 1][yGray + 1].PinkQueen)) {
            key = true;
            if (x == (xGray - 2) && y == (yGray + 2)) return true;
        }

        if (!key) {
            if ((fieldArray[x][y].Empty && x == (xGray + 1) && y == (yGray - 1)) || (fieldArray[x][y].Empty && x == (xGray + 1) && y == (yGray + 1))) {
                return true;
            }
            if ((fieldArray[xGray][yGray].GrayQueen && fieldArray[x][y].Empty && x == (xGray - 1) && y == (yGray - 1)) || (fieldArray[xGray][yGray].GrayQueen && fieldArray[x][y].Empty && x == (xGray - 1) && y == (yGray + 1)) || (fieldArray[xGray][yGray].GrayQueen && fieldArray[x][y].Empty && x == (xGray + 1) && y == (yGray + 1)) || (fieldArray[xGray][yGray].GrayQueen && fieldArray[x][y].Empty && x == (xGray + 1) && y == (yGray - 1))) {
                return true;
            }
        }
        return false;
    }

    private boolean eatingGray(int x, int y) {
        if ((fieldArray[xGray][yGray].GrayQueen || fieldArray[xGray][yGray].Gray) && x == (xGray + 2) && y == (yGray - 2) && fieldArray[x][y].Empty && (fieldArray[xGray + 1][yGray - 1].Pink || fieldArray[xGray + 1][yGray - 1].PinkQueen)) {
            if (fieldArray[xGray + 1][yGray - 1].Pink) fieldArray[xGray + 1][yGray - 1].Pink = false;
            else fieldArray[xGray + 1][yGray - 1].PinkQueen = false;
            fieldArray[xGray + 1][yGray - 1].Empty = true;
            setCountPinkCheckers(getCountPinkCheckers());
            return true;
        }
        if ((fieldArray[xGray][yGray].GrayQueen || fieldArray[xGray][yGray].Gray) && x == (xGray + 2) && y == (yGray + 2) && fieldArray[x][y].Empty && (fieldArray[xGray + 1][yGray + 1].Pink || fieldArray[xGray + 1][yGray + 1].PinkQueen)) {
            if (fieldArray[xGray + 1][yGray + 1].Pink) fieldArray[xGray + 1][yGray + 1].Pink = false;
            else fieldArray[xGray + 1][yGray + 1].PinkQueen = false;
            fieldArray[xGray + 1][yGray + 1].Empty = true;
            setCountPinkCheckers(getCountPinkCheckers());
            return true;
        }
        if (fieldArray[xGray][yGray].GrayQueen && x == (xGray - 2) && y == (yGray - 2) && fieldArray[x][y].Empty && (fieldArray[xGray - 1][yGray - 1].Pink || fieldArray[xGray - 1][yGray - 1].PinkQueen)) {
            if (fieldArray[xGray - 1][yGray - 1].Pink) fieldArray[xGray - 1][yGray - 1].Pink = false;
            else fieldArray[xGray - 1][yGray - 1].PinkQueen = false;
            fieldArray[xGray - 1][yGray - 1].Empty = true;
            setCountPinkCheckers(getCountPinkCheckers());
            return true;
        }
        if (fieldArray[xGray][yGray].GrayQueen && x == (xGray - 2) && y == (yGray + 2) && fieldArray[x][y].Empty && (fieldArray[xGray - 1][yGray + 1].Pink || fieldArray[xGray - 1][yGray + 1].PinkQueen)) {
            if (fieldArray[xGray - 1][yGray + 1].Pink) fieldArray[xGray - 1][yGray + 1].Pink = false;
            else fieldArray[xGray - 1][yGray + 1].PinkQueen = false;
            fieldArray[xGray - 1][yGray + 1].Empty = true;
            setCountPinkCheckers(getCountPinkCheckers());
            return true;
        }
        return false;
    }

    private void moveGray(int x, int y) {

        if (fieldArray[xGray][yGray].Gray) {
            if (x == 7) {
                fieldArray[x][y].GrayQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xGray][yGray].Empty = true;
                fieldArray[xGray][yGray].Gray = false;
            } else {
                fieldArray[x][y].Gray = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xGray][yGray].Empty = true;
                fieldArray[xGray][yGray].Gray = false;
            }
        } else {
            fieldArray[x][y].GrayQueen = true;
            fieldArray[x][y].Empty = false;
            fieldArray[xGray][yGray].Empty = true;
            fieldArray[xGray][yGray].GrayQueen = false;
        }

        xGray = x;
        yGray = y;
    }

    private boolean continueEatingGray() {
        if ((xGray + 2) < 8 && (yGray - 2) >= 0 && fieldArray[xGray + 2][yGray - 2].Empty && fieldArray[xGray + 1][yGray - 1].Pink) {
            return true;
        }
        if ((xGray + 2) < 8 && (yGray + 2) < 8 && fieldArray[xGray + 2][yGray + 2].Empty && fieldArray[xGray + 1][yGray + 1].Pink) {
            return true;
        }
        if (fieldArray[xGray][yGray].GrayQueen && (xGray - 2) >= 0 && (yGray - 2) >= 0 && fieldArray[xGray - 2][yGray - 2].Empty && (fieldArray[xGray - 1][yGray - 1].Pink || fieldArray[xGray - 1][yGray - 1].PinkQueen)) {
            return true;
        }
        if (fieldArray[xGray][yGray].GrayQueen && (xGray - 2) >= 0 && (yGray + 2) < 8 && fieldArray[xGray - 2][yGray + 2].Empty && (fieldArray[xGray - 1][yGray + 1].Pink && fieldArray[xGray - 1][yGray + 1].PinkQueen)) {
            return true;
        }
        return false;
    }

    /**
     *
     *
     *
     */

    private void clearCurrent() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fieldArray[i][j].Current = false;
            }
        }
    }

    /**
     *
     *
     *
     */

    public int getCountGrayCheckers() {
        return countGrayCheckers;
    }

    public void setCountGrayCheckers(int countGrayCheckers) {
        this.countGrayCheckers = countGrayCheckers - 1;
    }

    public int getCountPinkCheckers() {
        return countPinkCheckers;
    }

    public void setCountPinkCheckers(int countPinkCheckers) {
        this.countPinkCheckers = countPinkCheckers - 1;
    }

    /**
     *
     *
     *
     */

    private class Pair {
        public Pair() {
        }

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x;
        public int y;
    }

}
