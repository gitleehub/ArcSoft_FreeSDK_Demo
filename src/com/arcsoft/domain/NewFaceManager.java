package com.arcsoft.domain;

import com.arcsoft.AFD_FSDKLibrary;
import com.arcsoft.AFD_FSDK_FACERES;
import com.arcsoft.AFD_FSDK_Version;
import com.arcsoft.AFR_FSDKLibrary;
import com.arcsoft.AFR_FSDK_FACEINPUT;
import com.arcsoft.AFR_FSDK_FACEMODEL;
import com.arcsoft.AFR_FSDK_Version;
import com.arcsoft.ASVLOFFSCREEN;
import com.arcsoft.ASVL_COLOR_FORMAT;
import com.arcsoft.CLibrary;
import com.arcsoft.FaceInfo;
import com.arcsoft.MRECT;
import com.arcsoft._AFD_FSDK_OrientPriority;
import com.arcsoft.utils.BufferInfo;
import com.arcsoft.utils.ImageLoader;
import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.PointerByReference;

/**
* @author :lee
* @time	  :2018年6月21日 下午2:48:42
* @version:1
* @description:
*/
public class NewFaceManager {
	public static final int FD_WORKBUF_SIZE = 20 * 1024 * 1024;
	public static final int FR_WORKBUF_SIZE = 40 * 1024 * 1024;
	public static final int FAE_WORKBUF_SIZE = 30 * 1024 * 1024;
	public static final int FGE_WORKBUF_SIZE = 30 * 1024 * 1024;
	public static final int FT_WORKBUF_SIZE = 40 * 1024 * 1024;
	public static final int MAX_FACE_NUM = 5;

	private Pointer hFDEngine;
	private Pointer hFREngine;
	private Pointer hFAEEngine;
	private Pointer hFGEEngine;
	private Pointer hFTEngine;
	
	private Pointer pFDWorkMem;
	private Pointer pFRWorkMem;
	private Pointer pFAEWorkMem;
	private Pointer pFGEWorkMem;
	private Pointer pFTEWorkMem;
	
	private boolean isInitFD;
    private boolean isInitFR;
    private boolean isInitFT;
    private boolean isInitFAE;
    private boolean isInitFGE;
    
	public boolean bUseBGRToEngine = false;
	public boolean ifUseTracking = false;
	public boolean bUseRAWFile = false;
	
	 public int initFDEngine(){
	        if(isInitFD)
	            return 0;
	        pFDWorkMem = CLibrary.INSTANCE.malloc(FD_WORKBUF_SIZE);
	        PointerByReference phFDEngine = new PointerByReference();
	        NativeLong ret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_InitialFaceEngine(ArcIDAndKey.getAPPID(), ArcIDAndKey.getFdSdkkey(), pFDWorkMem, FD_WORKBUF_SIZE, phFDEngine, _AFD_FSDK_OrientPriority.AFD_FSDK_OPF_0_HIGHER_EXT, 32, MAX_FACE_NUM);
	        if (ret.longValue() != 0) {
	            CLibrary.INSTANCE.free(pFDWorkMem);
	            System.out.println(String.format("AFD_FSDK_InitialFaceEngine ret 0x%x",ret.longValue()));
	            return ret.intValue();
	        }
	        isInitFD = true;
	        hFDEngine = phFDEngine.getValue();
	        AFD_FSDK_Version versionFD = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_GetVersion(hFDEngine);
	        System.out.println(String.format("%d %d %d %d", versionFD.lCodebase, versionFD.lMajor, versionFD.lMinor, versionFD.lBuild));
	        System.out.println(versionFD.Version);
	        System.out.println(versionFD.BuildDate);
	        System.out.println(versionFD.CopyRight);
	        return 0;
	    }

	    /**
	     * @Title: initFREngine 
	     * @Description: 初始化FR引擎，0表示初始化成功
	     * @return int  
	     */
	    public int initFREngine(){
	        if(isInitFR)
	            return 0;
	        pFRWorkMem = CLibrary.INSTANCE.malloc(FR_WORKBUF_SIZE);
	        PointerByReference phFREngine = new PointerByReference();
	        NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_InitialEngine(ArcIDAndKey.getAPPID(), ArcIDAndKey.getFrSdkkey(), pFRWorkMem, FR_WORKBUF_SIZE, phFREngine);
	        if (ret.longValue() != 0) {
	            CLibrary.INSTANCE.free(pFRWorkMem);
	            System.out.println(String.format("AFR_FSDK_InitialEngine ret 0x%x" ,ret.longValue()));
	            return ret.intValue();
	        }
	        isInitFR = true;
	        hFREngine = phFREngine.getValue();
	        AFR_FSDK_Version versionFR = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_GetVersion(hFREngine);
	        System.out.println(String.format("%d %d %d %d", versionFR.lCodebase, versionFR.lMajor, versionFR.lMinor, versionFR.lBuild));
	        System.out.println(versionFR.Version);
	        System.out.println(versionFR.BuildDate);
	        System.out.println(versionFR.CopyRight);
	        return 0;
	    }
	    
	    /**
	     * 初始化所有引擎
	     * @return
	     */
	   public int initEngines(){
		   int initFDEngine = initFDEngine();
		   int initFREngine = initFREngine();
		   
		   if(initFDEngine==0&&initFREngine==0) {
			   return 0;
		   }else if(initFDEngine!=0){
			   return initFDEngine;
		   }else if(initFREngine!=0){
			   return initFREngine;
		   }else {
			   return -1;
		   }
	    }
	   
	   public int releaseEngines() {
		   NativeLong FDret = new NativeLong(-1);
		   NativeLong FRret = new NativeLong(-1);
		    if(isInitFD) {
		    	FDret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_UninitialFaceEngine(hFDEngine);
		    	if(FDret.intValue()==0) {
			    	isInitFD = false;
				    CLibrary.INSTANCE.free(pFDWorkMem);
		    	}
		    }
		    if(isInitFR) {
		    	FRret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_UninitialFaceEngine(hFDEngine);
		    	if(FRret.intValue()==0) {
			    	isInitFD = false;
				    CLibrary.INSTANCE.free(pFDWorkMem);
		    	}
		    }
		    if(FDret.intValue()!=0) {
		    	return FDret.intValue();
		    }
		    if(FRret.intValue()!=0) {
		    	return FRret.intValue();
		    }
		    return 0;
	   }
	
	//提取人脸特征的byte数组
	public byte[] getFaceModelFromImage(byte[] image) {
		ASVLOFFSCREEN loadImage = loadImage(image);
		byte[] byteArray;
		try {
			//doFaceDetection可能为空
			FaceInfo[] doFaceDetection = doFaceDetection(loadImage);
			AFR_FSDK_FACEMODEL extractFRFeature = extractFRFeature(loadImage,doFaceDetection[0]);
			//toByteArray抛出异常
			byteArray = extractFRFeature.toByteArray();
		} catch (Exception e) {
			return null;
		}
		return byteArray;
	}
	
	
	//两个FaceModel获取相似度
	public  float compareFaceSimilarity( byte[] modelA, byte[] modelB) {
        // Do Face Detect
       /* FaceInfo[] faceInfosA = doFaceDetection( inputImgA);
        if (faceInfosA.length < 1) {
            System.out.println("no face in Image A ");
            return 0.0f;
        }

        FaceInfo[] faceInfosB = doFaceDetection(inputImgB);
        if (faceInfosB.length < 1) {
            System.out.println("no face in Image B ");
            return 0.0f;
        }

        // Extract Face Feature
        AFR_FSDK_FACEMODEL faceFeatureA = extractFRFeature(inputImgA, faceInfosA[0]);
        if (faceFeatureA == null) {
            System.out.println("extract face feature in Image A failed");
            return 0.0f;
        }

        AFR_FSDK_FACEMODEL faceFeatureB = extractFRFeature( inputImgB, faceInfosB[0]);
        if (faceFeatureB == null) {
            System.out.println("extract face feature in Image B failed");
            faceFeatureA.freeUnmanaged();
            return 0.0f;
        }*/
		AFR_FSDK_FACEMODEL faceFeatureA;
		AFR_FSDK_FACEMODEL faceFeatureB;
		try {
			faceFeatureA = AFR_FSDK_FACEMODEL.fromByteArray(modelA);
			faceFeatureB = AFR_FSDK_FACEMODEL.fromByteArray(modelB);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0f;
		}
        // calc similarity between faceA and faceB
        FloatByReference fSimilScore = new FloatByReference(0.0f);
        NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_FacePairMatching(hFREngine, faceFeatureA, faceFeatureB, fSimilScore);
        faceFeatureA.freeUnmanaged();
        faceFeatureB.freeUnmanaged();
        if (ret.longValue() != 0) {
            System.out.println(String.format("AFR_FSDK_FacePairMatching failed:ret 0x%x" ,ret.longValue()));
            return 0.0f;
        }
        return fSimilScore.getValue();
    }
	
	public  AFR_FSDK_FACEMODEL extractFRFeature( ASVLOFFSCREEN inputImg, FaceInfo faceInfo) {

        AFR_FSDK_FACEINPUT faceinput = new AFR_FSDK_FACEINPUT();
        faceinput.lOrient = faceInfo.orient;
        faceinput.rcFace.left = faceInfo.left;
        faceinput.rcFace.top = faceInfo.top;
        faceinput.rcFace.right = faceInfo.right;
        faceinput.rcFace.bottom = faceInfo.bottom;

        AFR_FSDK_FACEMODEL faceFeature = new AFR_FSDK_FACEMODEL();
        NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_ExtractFRFeature(hFREngine, inputImg, faceinput, faceFeature);
        if (ret.longValue() != 0) {
            System.out.println(String.format("AFR_FSDK_ExtractFRFeature ret 0x%x" ,ret.longValue()));
            return null;
        }

        try {
            return faceFeature.deepCopy();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public  FaceInfo[] doFaceDetection(ASVLOFFSCREEN inputImg) {
        FaceInfo[] faceInfo = new FaceInfo[0];

        PointerByReference ppFaceRes = new PointerByReference();
        NativeLong ret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_StillImageFaceDetection(hFDEngine, inputImg, ppFaceRes);
        if (ret.longValue() != 0) {
            System.out.println(String.format("AFD_FSDK_StillImageFaceDetection ret 0x%x" , ret.longValue()));
            return faceInfo;
        }

        AFD_FSDK_FACERES faceRes = new AFD_FSDK_FACERES(ppFaceRes.getValue());
        if (faceRes.nFace > 0) {
            faceInfo = new FaceInfo[faceRes.nFace];
            for (int i = 0; i < faceRes.nFace; i++) {
                MRECT rect = new MRECT(new Pointer(Pointer.nativeValue(faceRes.rcFace.getPointer()) + faceRes.rcFace.size() * i));
                int orient = faceRes.lfaceOrient.getPointer().getInt(i * 4);
                faceInfo[i] = new FaceInfo();

                faceInfo[i].left = rect.left;
                faceInfo[i].top = rect.top;
                faceInfo[i].right = rect.right;
                faceInfo[i].bottom = rect.bottom;
                faceInfo[i].orient = orient;
            }
        }
        return faceInfo;
    }
	
	public  ASVLOFFSCREEN loadImage(byte[] filePath) {
        ASVLOFFSCREEN inputImg = new ASVLOFFSCREEN();
        
        BufferInfo bufferInfo = ImageLoader.getI420FromByteArray(filePath);
        inputImg.u32PixelArrayFormat = ASVL_COLOR_FORMAT.ASVL_PAF_I420;
        inputImg.i32Width = bufferInfo.width;
        inputImg.i32Height = bufferInfo.height;
        inputImg.pi32Pitch[0] = inputImg.i32Width;
        inputImg.pi32Pitch[1] = inputImg.i32Width / 2;
        inputImg.pi32Pitch[2] = inputImg.i32Width / 2;
        inputImg.ppu8Plane[0] = new Memory(inputImg.pi32Pitch[0] * inputImg.i32Height);
        inputImg.ppu8Plane[0].write(0, bufferInfo.buffer, 0, inputImg.pi32Pitch[0] * inputImg.i32Height);
        inputImg.ppu8Plane[1] = new Memory(inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
        inputImg.ppu8Plane[1].write(0, bufferInfo.buffer, inputImg.pi32Pitch[0] * inputImg.i32Height, inputImg.pi32Pitch[1] * inputImg.i32Height / 2);
        inputImg.ppu8Plane[2] = new Memory(inputImg.pi32Pitch[2] * inputImg.i32Height / 2);
        inputImg.ppu8Plane[2].write(0, bufferInfo.buffer, inputImg.pi32Pitch[0] * inputImg.i32Height + inputImg.pi32Pitch[1] * inputImg.i32Height / 2, inputImg.pi32Pitch[2] * inputImg.i32Height / 2);
        inputImg.ppu8Plane[3] = Pointer.NULL;
        
        inputImg.setAutoRead(false);
        return inputImg;
    }

	public Pointer gethFDEngine() {
		return hFDEngine;
	}

	public void sethFDEngine(Pointer hFDEngine) {
		this.hFDEngine = hFDEngine;
	}

	public Pointer gethFREngine() {
		return hFREngine;
	}

	public void sethFREngine(Pointer hFREngine) {
		this.hFREngine = hFREngine;
	}

	public Pointer gethFAEEngine() {
		return hFAEEngine;
	}

	public void sethFAEEngine(Pointer hFAEEngine) {
		this.hFAEEngine = hFAEEngine;
	}

	public Pointer gethFGEEngine() {
		return hFGEEngine;
	}

	public void sethFGEEngine(Pointer hFGEEngine) {
		this.hFGEEngine = hFGEEngine;
	}

	public Pointer gethFTEngine() {
		return hFTEngine;
	}

	public void sethFTEngine(Pointer hFTEngine) {
		this.hFTEngine = hFTEngine;
	}
	
	
}
