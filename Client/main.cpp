#include "sciter-x.h"
#include "sciter-x-window.hpp"

class frame: public sciter::window {
public:
  frame() : window(SW_TITLEBAR | SW_RESIZEABLE | SW_CONTROLS | SW_MAIN | SW_ENABLE_DEBUG) {}

  // map of native functions exposed to script:
  BEGIN_FUNCTION_MAP
    FUNCTION_0("nativeMessage", nativeMessage);
  END_FUNCTION_MAP
  // function expsed to script:
  sciter::string  nativeMessage() { return WSTR("Hello C++ World"); }

};

#include "resources.cpp" // resources packaged into binary blob.

int uimain(std::function<int()> run ) {

  sciter::archive::instance().open(aux::elements_of(resources)); // bind resources[] (defined in "resources.cpp") with the archive

  aux::asset_ptr<frame> pwin = new frame();

  // note: this:://app URL is dedicated to the sciter::archive content associated with the application
  pwin->load( WSTR("this://app/main.htm") );
  //or use this to load UI from  
  //  pwin->load( WSTR("file:///home/andrew/Desktop/Project/res/main.htm") );
  
  pwin->expand();

  return run();
}
