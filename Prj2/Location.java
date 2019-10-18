import java.lang.StringBuilder;
import java.util.Scanner;

/* Changes from project 1:
 *
 * The Location class now represents a word, instead of a row/column position.
 *
 * The iteration methods start() and nextNeighbor() are no longer const methods.
 * However, they still serve the same purposes of starting and continuing
 * iteration. Note that neither method should modify the "word" data member; 
 * they should only modify the two iteration-related data members.
 *
 * Consider a Location object called 'x'. Repeatedly calling x.nextNeighbor()
 * should produce all Locations that are different from x by doing one of the
 * following things:
 *  1. changing a letter in x
 *  2. inserting a new letter in x
 *  3. deleting a letter from x
 * The nextNeighbor method should never produce x itself (x is not a neighbor of
 * itself). The order of iteration should be as follows:
 *  1. Change each letter of x to a different letter, from the first letter to
 *     the last (i.e. x.word[0], x.word[1], ...). Change each letter in alphabet
 *     order (i.e. a, b, c, ..., z).
 *  2. Insert a letter at each position of x, from the first position to the
 *     last (i.e. before x.word[0], before x.word[1], etc.). Try each possible
 *     letter in alphabet order.
 *  3. Delete a letter from each position of x, from the first letter to the
 *     last (i.e. x.word[0], x.word[1], etc.);
 *  After performing all these steps, iteration will be done.
 *
 * As an example, here is the way iteration should work on the word "and"
 * (read the first row left to right, then the second row, etc.):
 *  bnd, cnd, dnd, end, fnd, gnd, hnd, ind, jnd, knd, lnd, mnd, nnd, ond, pnd,
 *  qnd, rnd, snd, tnd, und, vnd, wnd, xnd, ynd, znd, aad, abd, acd, add, aed,
 *  afd, agd, ahd, aid, ajd, akd, ald, amd, aod, apd, aqd, ard, asd, atd, aud,
 *  avd, awd, axd, ayd, azd, ana, anb, anc, ane, anf, ang, anh, ani, anj, ank,
 *  anl, anm, ann, ano, anp, anq, anr, ans, ant, anu, anv, anw, anx, any, anz,
 *  aand, band, cand, dand, eand, fand, gand, hand, iand, jand, kand, land,
 *  mand, nand, oand, pand, qand, rand, sand, tand, uand, vand, wand, xand,
 *  yand, zand, aand, abnd, acnd, adnd, aend, afnd, agnd, ahnd, aind, ajnd,
 *  aknd, alnd, amnd, annd, aond, apnd, aqnd, arnd, asnd, atnd, aund, avnd,
 *  awnd, axnd, aynd, aznd, anad, anbd, ancd, andd, aned, anfd, angd, anhd,
 *  anid, anjd, ankd, anld, anmd, annd, anod, anpd, anqd, anrd, ansd, antd,
 *  anud, anvd, anwd, anxd, anyd, anzd, anda, andb, andc, andd, ande, andf,
 *  andg, andh, andi, andj, andk, andl, andm, andn, ando, andp, andq, andr,
 *  ands, andt, andu, andv, andw, andx, andy, andz, nd, ad, an
 * Notice that the word "and" does not appear in this list.
 *
 * The isLess method provides a means of comparing two Locations for purposes of
 * keeping them in order. This is useful for sorting Locations and for putting
 * them in an ordered collection, like a binary search tree, or Java set or
 * map.  The isLess method should compare based on the word only, and not on
 * iteration status.
 *
 * Some comments on the data members:
 *  - "word" contains, of course, the string that this Location represents.
 *
 *  - "iterationMode" should reflect which mode the iteration is currently in,
 *    which is one of CHANGE_LETTER, INSERT_LETTER, DELETE_LETTER, or DONE.
 *
 *  - "indexToChange" contains the index into "word" that should be changed next
 *    (on the next call to nextNeighbor()). This is used repeatedly for each mode.
 *
 *  - "nextLetter" contains the character that should be used next to change or
 *    insert (for the modes CHANGE_LETTER and INSERT_LETTER) on the next call to
 *    nextNeighbor().
 */

class Location implements Comparable<Location> {
  final int CHANGE_LETTER = 0;
  final int INSERT_LETTER = 1;
  final int DELETE_LETTER = 2;
  final int DONE          = 3;

  StringBuilder word;
  int iterationMode;
  int indexToChange;
  char nextLetter;

  Location() {
    StringBuilder word = new StringBuilder();
  }

  void start() {  
    iterationMode = CHANGE_LETTER;
    indexToChange = 0;
    nextLetter = 'a';
  }

  Location nextNeighbor() {  
    Location temp = new Location();
    temp.word = new StringBuilder(word);
    switch (iterationMode){
      case CHANGE_LETTER:
        temp.word.setCharAt(indexToChange, nextLetter);
        nextLetter++;
        //if all letters have been checked, raise index and reset letter
        if(nextLetter == '{'){
          indexToChange++;
          nextLetter = 'a';
            //if all indexes have been checked, reset index and raise iteration mode
            if(indexToChange == word.length()){
              iterationMode++;
              indexToChange = 0;
            }
          }
        break;
      case INSERT_LETTER:
        temp.word.insert(indexToChange, nextLetter);
        nextLetter++;
        if (nextLetter == '{'){
          indexToChange++;
          nextLetter = 'a';
          if(indexToChange > word.length()){
            iterationMode++;
            indexToChange = 0;
          }
        }
        break;
      case DELETE_LETTER:
        temp.word.deleteCharAt(indexToChange);
        indexToChange++;
        if(indexToChange == word.length()){
          iterationMode++;
        }
        break;
      case DONE:
        break;
    }
    
    return temp;
  }

  //Checks all iteration variables to see if isDone() should return true
  boolean isDone() {  
    if(iterationMode == DONE){
        return true;
    }
    else return false;
  }

  //returns true if loc.word = this.word
  boolean isEqual(Location loc) {  
    if(this.word.toString().equals(loc.word.toString())){
      return true;
    }
    else return false;
  }

  void streamOut() {
    System.out.println(word);
  }

  void streamIn(Scanner input) {
    this.word = new StringBuilder(input.next());
  }

//returns true if this.word is less than loc.word
  boolean isLess(Location loc) {
    int compareResult = word.toString().compareTo(loc.word.toString());
    if(compareResult < 0){
      return true;
    }
    else return false;
  }

  public int compareTo(Location loc) {
    //if the words are equal return 0 
    if (word.toString().equals(loc.word.toString()))
      return 0;
    //if the original is smaller, return -1
    else if(word == null)
      return -1;
    else if (loc.word == null)
      return 1;
    else if (this.isLess(loc))
      return -1;
    else 
      return 1;

  }

}
