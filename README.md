# aes-cmac
java version aes-cmac，meet the rfc-4493
the following 4 type  test data can be used to verify.
(from  https://csrc.nist.gov/CSRC/media/Projects/Cryptographic-Standards-and-Guidelines/documents/examples/AES_CMAC.pdf)


CMAC-AES128

Example #1

Key is
 2B7E1516 28AED2A6 ABF71588 09CF4F3C
Mlen=0

PT is
<empty>

Full Blocks
 L
 7DF76B0C 1AB899B3 3E42F047 B91B546F
Last Block
 K2:
 F7DDAC30 6AE266CC F90BC11E E46D513B
Block #0
 inBlock = 77DDAC30 6AE266CC F90BC11E E46D513B
outBlock = BB1D6929 E9593728 7FA37D12 9B756746
Tag is
BB1D6929 E9593728 7FA37D12 9B756746
==============================================================
 
--------------------------------------------------------------
----------------------------
--------------------------------------------------------------
Example #2

Key is
 2B7E1516 28AED2A6 ABF71588 09CF4F3C
Mlen=16

PT is
6BC1BEE2 2E409F96 E93D7E11 7393172A
Full Blocks
 L
 7DF76B0C 1AB899B3 3E42F047 B91B546F
Last Block
 K1:
 FBEED618 35713366 7C85E08F 7236A8DE
Block #1
 inBlock = 902F68FA 1B31ACF0 95B89E9E 01A5BFF4
outBlock = 070A16B4 6B4D4144 F79BDD9D D04A287C
Tag is
070A16B4 6B4D4144 F79BDD9D D04A287C
==============================================================

Example #3

Key is
 2B7E1516 28AED2A6 ABF71588 09CF4F3C
Mlen=20

PT is
6BC1BEE2 2E409F96 E93D7E11 7393172A 
--------------------------------------------------------------
----------------------------
--------------------------------------------------------------
--------------------------------------------------------------
 L
 AE2D8A57
Full Blocks
 7DF76B0C 1AB899B3 3E42F047 B91B546F
Block #1
 inBlock = 6BC1BEE2 2E409F96 E93D7E11 7393172A
outBlock = 3AD77BB4 0D7A3660 A89ECAF3 2466EF97
Last Block
 K2:
 F7DDAC30 6AE266CC F90BC11E E46D513B
Block #2
 inBlock = 63275DD3 E79850AC 51950BED C00BBEAC
outBlock = 7D85449E A6EA19C8 23A7BF78 837DFADE
Tag is
7D85449E A6EA19C8 23A7BF78 837DFADE
==============================================================

Example #4

Key is
 2B7E1516 28AED2A6 ABF71588 09CF4F3C
Mlen=64

PT is
6BC1BEE2 2E409F96 E93D7E11 7393172A
AE2D8A57 1E03AC9C 9EB76FAC 45AF8E51
30C81C46 A35CE411 E5FBC119 1A0A52EF
F69F2445 DF4F9B17 AD2B417B E66C3710 
----------------------------
--------------------------------------------------------------
--------------------------------------------------------------
 L
Full Blocks
 7DF76B0C 1AB899B3 3E42F047 B91B546F
Block #1
 inBlock = 6BC1BEE2 2E409F96 E93D7E11 7393172A
outBlock = 3AD77BB4 0D7A3660 A89ECAF3 2466EF97
Block #2
 inBlock = 94FAF1E3 13799AFC 3629A55F 61C961C6
outBlock = B148C17F 309EE692 287AE57C F12ADD49
Block #3
 inBlock = 8180DD39 93C20283 CD812465 EB208FA6
outBlock = C93D11BF AF08C5DC 4D90B37B 4DEE002B
Last Block
 K1:
 FBEED618 35713366 7C85E08F 7236A8DE
Block #4
 inBlock = C44CE3E2 45366DAD 9C3E128F D9B49FE5
outBlock = 51F0BEBF 7E3B9D92 FC497417 79363CFE
Tag is
51F0BEBF 7E3B9D92 FC497417 79363CFE
