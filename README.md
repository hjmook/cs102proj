# cs102proj
CARD GAME IDEA - BABI!

1. each player is given 4 cards, signifying their 4 lives

2. the goal is to eliminate the other players, by decreasing their lives, aka reducing the number of cards they have

3. main pool: where player take their cards

4. secondary pool: where player puts down the card (one-by-one)

3. limits of the game: 
  a. when the total sum reaches 100, the next player should be able to get out of the situation by the following power cards (if they have)
    1. any face card of value 4, can either
      i) add 4 to the sum (in general, not when sum is 100)
      ii) reverse play
    2. any face card of value 7, can either
      i) add 7 to the sum (in general, not when sum is 100)
      ii) skip play
    3. any Jack royal, can either
      i) add 10 to the sum (in general, not when sum is 100)
      ii) minus 10 from the sum
    4. any Queen royal, can either
      i) add 20 to the sum (in general, not when sum is 100)
      ii) minus 20 from the sum
    5. king straight to 100 (any case)
  b. if the player is not able to get out of the situation where the total sum is 100, then they have to lose a card (lose a live)
  
4. when do we reshuffle the cards?
  a. when there are less than 2 cards left in the main pool
  b. when one player loses a lives
  
5. what happens when the cards are reshuffled?
  a. just open the top card after reshuffling
  b. if the first card is a number card, then continue
  c. if the first card is a royal, then insert back into the deck, and keep on taking cards until the top card is a face value
  
6. after placing a card down into the secondary pool, players will replace with a new card from the main pool, depending on how many lives they have
  
7. when restarting: 
  a) if there are less than 2 cards left in the main pool, the next player will start //this takes precendence
  b) if the player loses a life, the player who lost the life will start

    
things to take note: 
  1. cards have 4 faces: diamond, heart, club, spades
  2. cards have 10 number cards: ace (one), two, three, four, five, six, seven, eight, nine, ten
  3. cards have 3 royal cards: jack (+- 10), queen (+- 10), king (straight to 100)
  4. functions good to have: 
    a) display cards
    b) take a card from main pool to replace (after putting down a card in the main pool)
    b) place card into deck (at random) when a live is lost
    c) option to skip play OR add 7 to total sum (when put down number 7) 
    d) option to reverse play OR add 4 to total sum (when put down number 4)
    e) option to either plus or minus 10 to total sum (when put down jack)
    g) option to either plus or minus 20 to total sum (when put down queen)
    h) increase sum straight to 100 (when put down king)
    i) display current total sum
    j) put cards from secondary pool to main pool
    k) shuffle cards when restart
    l) take the top card from the deck and put into hand to replace card
    m) put a card in the middle of the deck (either when top card is a royal OR when player loses a life)
    o) let players check the sum of each of their options before putting down a card
    
  5. players have only 4 lives 
  6. things to keep track: 
    a) current total sum 
    b) how many cards are left in the deck
    c) each player's lives
    d) number of players left

notes: 
  1. maybe can use stack for this?2
  2. classes to have (?)
    i) deck
    ii) hand
    iii) player
    iiii) card 
    iiiii) babi!
    iiiiii) main pool
    iiiiiii) secondary pool


step: 
  1. get the number of player (main program - babi)
    - create instance of the number of players
    - create instance of the number of player's hands (4 cards each) at random
    - decrease the cards in the deck
  
  2. create an instance of a fresh deck of cards
    - invoke shuffling to shuffle the cards
    
  3. randomly generate the first player to start the game
  
  4. open the first card from the main pool and insert into the secondary pool
    - if royal, then put card in middle of deck (continue doing so till  number card)
    - if number card, add to sum