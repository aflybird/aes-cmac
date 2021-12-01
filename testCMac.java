

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class TestCmac {

	public static void main(String[] args) {

		System.out.println("");

		byte[] cbcKey = {
							(byte)0x2b,
							(byte)0x7e,
							(byte)0x15,
							(byte)0x16,
							(byte)0x28,
							(byte)0xae,
							(byte)0xd2,
							(byte)0xa6,
							(byte)0xab,
							(byte)0xf7,
							(byte)0x15,
							(byte)0x88,
							(byte)0x09,
							(byte)0xcf,
							(byte)0x4f,
							(byte)0x3c
						};

		byte[] key1 = new byte[16];
		byte[] key2 =new byte[16] ;

		byte[] iv = new byte[16] ;

	

		byte[] text= {
			
			(byte)0x6b,
			(byte)0xc1,
			(byte)0xbe,
			(byte)0xe2,
			(byte)0x2e,
			(byte)0x40,
			(byte)0x9f,
			(byte)0x96,
			(byte)0xe9,
			(byte)0x3d,
			(byte)0x7e,
			(byte)0x11,
			(byte)0x73,
			(byte)0x93,
			(byte)0x17,
			(byte)0x2a,
			(byte)0xae,
			(byte)0x2d,
			(byte)0x8a,
			(byte)0x57
			
		};
		
		CmacKeys keys = new CmacKeys();
		keys.setCbcKey(cbcKey);
		
		keys.generate_subkey(cbcKey,key1,key2);
		keys.setKey1(key1);
		keys.setKey2(key2);
		Cmac cmac = Cmac.compute(iv, text, keys);


		System.out.printf("%02X",  cmac.getMac()[0] );
		System.out.printf("%02X",  cmac.getMac()[1] );
		System.out.printf("%02X",  cmac.getMac()[2] );
		System.out.printf("%02X",  cmac.getMac()[3] );
		System.out.printf("%02X",  cmac.getMac()[4] );
		System.out.printf("%02X",  cmac.getMac()[5] );
		System.out.printf("%02X",  cmac.getMac()[6] );
		System.out.printf("%02X",  cmac.getMac()[7] );
		System.out.printf("%02X",  cmac.getMac()[8] );
		System.out.printf("%02X",  cmac.getMac()[9] );
		System.out.printf("%02X",  cmac.getMac()[10] );
		System.out.printf("%02X",  cmac.getMac()[11] );
		System.out.printf("%02X",  cmac.getMac()[12] );
		System.out.printf("%02X",  cmac.getMac()[13] );
		System.out.printf("%02X",  cmac.getMac()[14] );
		System.out.printf("%02X",  cmac.getMac()[15] );

		

	}

}





 class Cmac {

	public Cmac() {
		this.mac = null;
	}

	byte[] mac;

	public byte[] getMac() {
		return mac;
	}

	public void setMac(byte[] mac) {
		this.mac = mac;
	}

	public static Cmac compute(byte[] iv, byte[] data, CmacKeys keys){

		Cmac cMac = new Cmac();

		try {

			int  nBlocks = data.length/16;

			int  lastBlen = data.length%16;

		byte[] lastState = new byte[16];
		
		
		byte[] lastBData = new byte[16];
		

			boolean padding=false;

			if(lastBlen> 0){
				padding = true;
				nBlocks++;
			}


			if(nBlocks > 1){

				byte[] cbcdata =  Arrays.copyOf(data, (nBlocks-1)*16);

				//CBC
				SecretKeySpec aesKey= new SecretKeySpec(keys.getCbcKey(),"AES");
				IvParameterSpec ivparam = new IvParameterSpec(iv);
				Cipher cbc = Cipher.getInstance("AES/CBC/NoPadding");
				cbc.init(Cipher.ENCRYPT_MODE,aesKey,ivparam);

				byte[] cbcCt = cbc.doFinal(cbcdata);

				//Get cbc last state
				lastState = Arrays.copyOfRange(cbcCt,(nBlocks-2)*16, (nBlocks-1)*16);

				lastBData = Arrays.copyOfRange(data,(nBlocks-1)*16 ,(nBlocks)*16);

			}else{

				if(data.length==0){
					padding=true;

				}else{
					lastBData = Arrays.copyOfRange(data,(nBlocks-1)*16 ,(nBlocks)*16);
				}
			}


			//Add Padding if needed
			if(lastBlen!=0 ||padding){
				lastBData[lastBlen]= (byte) 128;
			}


			if(padding){
				for (int i = 0; i < 16; i++) {
					lastBData[i] = (byte) ( (lastBData[i]) ^ ( keys.getKey2()[i]));
				}
			}else{
				for (int i = 0; i < 16; i++) {
					lastBData[i] = (byte) ( (lastBData[i]) ^ ( keys.getKey1()[i]));
				}

			}

			SecretKeySpec aesKey= new SecretKeySpec(keys.getCbcKey(),"AES");
			IvParameterSpec ivparam = new IvParameterSpec(lastState);
			Cipher aesMac = Cipher.getInstance("AES/CBC/NoPadding");
			aesMac.init(Cipher.ENCRYPT_MODE,aesKey, ivparam);

			cMac.setMac(aesMac.doFinal(lastBData));

		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cMac;
	}

}

 class CmacKeys {

	byte[] key1;
	byte[] key2;
	byte[] cbcKey;

	public CmacKeys() {
		this.key1=null;
		this.key2=null;
		this.cbcKey=null;
	}

	public CmacKeys(byte[] key1, byte[] key2, byte[] cbcRawKey) {
		super();
		this.key1 = key1;
		this.key2 = key2;
		this.cbcKey = cbcRawKey;
	}



	public byte[] getKey1() {
		return key1;
	}

	public void setKey1(byte[] key1) {
		this.key1 = key1;
	}

	public byte[] getKey2() {
		return key2;
	}

	public void setKey2(byte[] key2) {
		this.key2 = key2;
	}

	public byte[] getCbcKey() {
		return cbcKey;
	}

	public void setCbcKey(byte[] cbcKey) {
		this.cbcKey = cbcKey;
	}
	
	
	 
  void xor_128(byte[] a, byte[] b, byte[] out)
  {
      int i;
      for (i= 0;i< 16; i++ )
      {
          out[i] = (byte)(((int)a[i]) ^ ((int)b[i]) );
      }
  }
  
  
  
  
  void leftshift_onebit( byte[] input ,byte[] output)
  {
      byte         i;
      byte overflow = 0;

      for ( i=15; i>=0; i-- ) {
          output[i] = (byte)(input[i] << 1);
          output[i] |= overflow;
		  if( (input[i] & 0x80) > 0  )
		  {
			  overflow = 1;
		  }
		  else
		  {
			  overflow = 0;
		  }
		  
      }
      return;
  }

  
  
  void generate_subkey(byte[] key, byte[] K1, byte[] K2)
  {
      byte[] L  = new byte[16];
      byte[] Z  = new byte[16];
      byte[] tmp= new byte[16];
      int i;
	  
      byte[] const_Rb = 
      {
		  (byte)0x00, 
		  (byte)0x00,
		  (byte)0x00,
		  (byte)0x00,
		  (byte)0x00, 
		  (byte)0x00, 
		  (byte)0x00, 
		  (byte)0x00,
		  (byte)0x00, 
		  (byte)0x00, 
		  (byte)0x00, 
		  (byte)0x00, 
		  (byte)0x00, 
		  (byte)0x00, 
		  (byte)0x00, 
		  (byte)0x87
      };
	  

	  byte[] iv =  new byte[16] ;

      try
	  {
		  SecretKeySpec aesKey= new SecretKeySpec(key,"AES");
		  IvParameterSpec ivparam = new IvParameterSpec(iv);
		  Cipher cbc = Cipher.getInstance("AES/CBC/NoPadding");
		  cbc.init(Cipher.ENCRYPT_MODE,aesKey,ivparam);

		  L = cbc.doFinal(Z);
	  }
	  catch (Exception  e) 
	  {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
	  
      if ( (L[0] & 0x80) == 0 ) 
	  { /* If MSB(L) = 0, then K1 = L << 1 */
          leftshift_onebit(L,K1);
      } 
	  else 
	  {    /* Else K1 = ( L << 1 ) (+) Rb */
          leftshift_onebit(L,tmp);
          xor_128(tmp,const_Rb,K1);
      }

      if ( (K1[0] & 0x80) == 0 ) {
          leftshift_onebit(K1,K2);
      } else {
          leftshift_onebit(K1,tmp);
          xor_128(tmp,const_Rb,K2);
      }
      return;
  }
  

}
