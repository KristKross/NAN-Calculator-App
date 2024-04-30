package com.example.tamistime
import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // logic to process which button is pressed and which dessert is chosen
    private var selectedItem = 0
    private var selectedDessert = ""
    private lateinit var buttonFx: MediaPlayer
    private lateinit var whooshFx: MediaPlayer
    private lateinit var dropFx: MediaPlayer
    private lateinit var stirFx: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonFx = MediaPlayer.create(this, R.raw.pop)
        whooshFx = MediaPlayer.create(this, R.raw.whoosh)
        dropFx = MediaPlayer.create(this, R.raw.metalhit)
        stirFx = MediaPlayer.create(this, R.raw.stir)
    }

    // function to start the app
    fun start(view: View) {
        val rice = findViewById<ImageButton>(R.id.rice)
        val coconut = findViewById<ImageButton>(R.id.coconut)
        val back = findViewById<Button>(R.id.back1)
        val confirm = findViewById<Button>(R.id.confirm1)
        val text = findViewById<TextView>(R.id.ingredientText)
        val background = findViewById<LinearLayout>(R.id.background)
        val menuLayout = findViewById<LinearLayout>(R.id.menuLayout)
        val firstChoice = findViewById<LinearLayout>(R.id.firstChoice)


        // disables buttons so users can't spam
        disableButtonsTemporarily()

        // changes the background
        background.setBackgroundResource(R.drawable.countertopbackground)

        // pops up assets and makes them invisible
        firstChoice.visibility = View.VISIBLE
        menuLayout.visibility = View.GONE

        rice.visibility = View.VISIBLE
        coconut.visibility = View.VISIBLE
        text.visibility = View.VISIBLE

        back.visibility = View.GONE
        confirm.visibility = View.GONE
    }

    // function to show instructions on how to use the app
    fun showPopup(view: View) {
        val dialog = Dialog(this) // variable name of window
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(R.drawable.popupbg) // adds a background to the window
        dialog.setContentView(R.layout.tutorialpopup)


        // disables button
        disableButtonsTemporarily()

        // variable of skip button
        val skipTextView = dialog.findViewById<TextView>(R.id.skip)

        skipTextView.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    // function for the first choice of two items
    fun firstChoiceRice(view: View) {
        Toast.makeText(this, "You chose rice!", Toast.LENGTH_SHORT).show() // a toast message to show what item user chose
        selectedItem = 1 // tells the programme the item number

        val rice = findViewById<ImageButton>(R.id.rice)
        val coconut = findViewById<ImageButton>(R.id.coconut)
        val back = findViewById<Button>(R.id.back1)
        val confirm = findViewById<Button>(R.id.confirm1)
        val text = findViewById<TextView>(R.id.ingredientText)
        val riceImage = findViewById<ImageView>(R.id.riceImage)

        buttonFx.start()

        // disables button
        disableButtonsTemporarily()

        // adds a fading animation to buttons for effect
        fadeOut(rice)
        fadeOut(coconut)
        fadeOut(text)

        fadeIn(back)
        fadeIn(confirm)
        fadeIn(riceImage)

        // starts an animation for item to come in frame from top
        riceImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_top))
    }

    fun firstChoiceCoconut(view: View) {
        Toast.makeText(this, "You chose coconut!", Toast.LENGTH_SHORT).show()
        selectedItem = 2 // tells the programme the item number


        val rice = findViewById<ImageButton>(R.id.rice)
        val coconut = findViewById<ImageButton>(R.id.coconut)
        val back = findViewById<Button>(R.id.back1)
        val confirm = findViewById<Button>(R.id.confirm1)
        val text = findViewById<TextView>(R.id.ingredientText)
        val coconutImage = findViewById<ImageView>(R.id.coconutImage)

        buttonFx.start()

        // disables button
        disableButtonsTemporarily()

        // adds a fading animation to buttons for effect
        fadeOut(rice)
        fadeOut(coconut)
        fadeOut(text)

        fadeIn(back)
        fadeIn(confirm)
        fadeIn(coconutImage)

        // starts an animation for item to come in frame from top
        coconutImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_top))
    }

    fun back1(view: View) {
        val rice = findViewById<ImageButton>(R.id.rice)
        val coconut = findViewById<ImageButton>(R.id.coconut)
        val back = findViewById<Button>(R.id.back1)
        val confirm = findViewById<Button>(R.id.confirm1)
        val text = findViewById<TextView>(R.id.ingredientText)
        val riceImage = findViewById<ImageView>(R.id.riceImage)
        val coconutImage = findViewById<ImageView>(R.id.coconutImage)

        whooshFx.start()

        // disables button
        disableButtonsTemporarily()

        // adds a fading animation to buttons for effect
        fadeOut(back)
        fadeOut(confirm)

        fadeIn(rice)
        fadeIn(coconut)
        fadeIn(text)

        // slides the item back out of frame depending on the selected item
        if (selectedItem == 1) {
            riceImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_back))
            riceImage.visibility = View.INVISIBLE
        }
        else {
            coconutImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_back))
            coconutImage.visibility = View.INVISIBLE
        }
    }

    fun confirm1(view: View) {
        val firstChoice = findViewById<LinearLayout>(R.id.firstChoice)
        val secondChoice = findViewById<LinearLayout>(R.id.secondChoice)
        val bowlImage = findViewById<ImageView>(R.id.bowlImage)
        val riceImage = findViewById<ImageView>(R.id.riceImage)
        val coconutImage = findViewById<ImageView>(R.id.coconutImage)

        // disables button
        disableButtonsTemporarily()

        // stores the number into a new variable
        selectedDessert += selectedItem.toString()

        // fades out the screen
        fadeOut(firstChoice)

        // plays an animation that slides a bowl from the left
        bowlImage.visibility = View.VISIBLE
        bowlImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))

        // adds a drop animation to the ingredient into the bowl depending on selected item
        if (selectedItem == 1) {
            val delayMillis = 1000L
            Handler().postDelayed({
                riceImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.drop_in_bowl))
                riceImage.visibility = View.GONE
            }, delayMillis)
        } else {
            val delayMillis = 1000L
            Handler().postDelayed({
                coconutImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.drop_in_bowl))
                coconutImage.visibility = View.GONE
            }, delayMillis)
        }

        Handler().postDelayed({
            dropFx.start()
        }, 1500L)

        // slides the bowl back out of frame
        val bowlMoveLeftDelay = 2000L
        Handler().postDelayed({
            bowlImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_to_left))
            bowlImage.visibility = View.GONE
        }, bowlMoveLeftDelay)

        // changes the scene
        val changeSceneDelay = 3000L
        Handler().postDelayed({
            val yam = findViewById<ImageButton>(R.id.yam)
            val bean = findViewById<ImageButton>(R.id.bean)
            val back = findViewById<Button>(R.id.back2)
            val confirm = findViewById<Button>(R.id.confirm2)
            val text = findViewById<TextView>(R.id.ingredientText2)

            yam.visibility = View.VISIBLE
            bean.visibility = View.VISIBLE
            text.visibility = View.VISIBLE

            back.visibility = View.INVISIBLE
            confirm.visibility = View.INVISIBLE

            secondChoice.visibility = View.VISIBLE
        }, changeSceneDelay)
    }

    //
    // Everything below is the same repeated but with different variables
    //

    fun secondChoiceYam(view: View) {
        selectedItem = 1
        Toast.makeText(this, "You chose purple yam!", Toast.LENGTH_SHORT).show()

        val yam = findViewById<ImageButton>(R.id.yam)
        val bean = findViewById<ImageButton>(R.id.bean)
        val back = findViewById<Button>(R.id.back2)
        val confirm = findViewById<Button>(R.id.confirm2)
        val text = findViewById<TextView>(R.id.ingredientText2)
        val yamImage = findViewById<ImageView>(R.id.yamImage)

        buttonFx.start()

        disableButtonsTemporarily()

        fadeOut(yam)
        fadeOut(bean)
        fadeOut(text)

        fadeIn(back)
        fadeIn(confirm)
        fadeIn(yamImage)

        yamImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_top))
    }

    fun secondChoiceBean(view: View) {
        selectedItem = 2
        Toast.makeText(this, "You chose mung beans!", Toast.LENGTH_SHORT).show()

        val yam = findViewById<ImageButton>(R.id.yam)
        val bean = findViewById<ImageButton>(R.id.bean)
        val back = findViewById<Button>(R.id.back2)
        val confirm = findViewById<Button>(R.id.confirm2)
        val text = findViewById<TextView>(R.id.ingredientText2)
        val beanImage = findViewById<ImageView>(R.id.beanImage)

        buttonFx.start()

        disableButtonsTemporarily()

        fadeOut(yam)
        fadeOut(bean)
        fadeOut(text)

        fadeIn(back)
        fadeIn(confirm)
        fadeIn(beanImage)

        beanImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_top))
    }

    fun back2(view: View) {
        val yam = findViewById<ImageButton>(R.id.yam)
        val bean = findViewById<ImageButton>(R.id.bean)
        val back = findViewById<Button>(R.id.back2)
        val confirm = findViewById<Button>(R.id.confirm2)
        val text = findViewById<TextView>(R.id.ingredientText2)
        val yamImage = findViewById<ImageView>(R.id.yamImage)
        val beanImage = findViewById<ImageView>(R.id.beanImage)

        whooshFx.start()

        disableButtonsTemporarily()

        fadeOut(back)
        fadeOut(confirm)

        fadeIn(yam)
        fadeIn(bean)
        fadeIn(text)

        if (selectedItem == 1) {
            yamImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_back))
            yamImage.visibility = View.INVISIBLE
        }
        else {
            beanImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_back))
            beanImage.visibility = View.INVISIBLE
        }
    }

    fun confirm2(view: View) {
        val secondChoice = findViewById<LinearLayout>(R.id.secondChoice)
        val thirdChoice = findViewById<LinearLayout>(R.id.thirdChoice)
        val bowlImage = findViewById<ImageView>(R.id.bowlImage)
        val yamImage = findViewById<ImageView>(R.id.yamImage)
        val beanImage = findViewById<ImageView>(R.id.beanImage)

        selectedDessert += selectedItem.toString()

        disableButtonsTemporarily()

        fadeOut(secondChoice)

        bowlImage.visibility = View.VISIBLE
        bowlImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))

        if (selectedItem == 1) {
            val delayMillis = 1000L
            Handler().postDelayed({
                yamImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.drop_in_bowl))
                yamImage.visibility = View.GONE
            }, delayMillis)
        } else {
            val delayMillis = 1000L
            Handler().postDelayed({
                beanImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.drop_in_bowl))
                beanImage.visibility = View.GONE
            }, delayMillis)
        }

        Handler().postDelayed({
            dropFx.start()
        }, 1500L)

        val bowlMoveLeftDelay = 2000L
        Handler().postDelayed({
            bowlImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_to_left))
            bowlImage.visibility = View.GONE
        }, bowlMoveLeftDelay)

        val changeSceneDelay = 3000L
        Handler().postDelayed({
            val jackfruit = findViewById<ImageButton>(R.id.jackfruit)
            val banana = findViewById<ImageButton>(R.id.banana)
            val back = findViewById<Button>(R.id.back3)
            val confirm = findViewById<Button>(R.id.confirm3)
            val text = findViewById<TextView>(R.id.ingredientText3)

            jackfruit.visibility = View.VISIBLE
            banana.visibility = View.VISIBLE
            text.visibility = View.VISIBLE

            back.visibility = View.INVISIBLE
            confirm.visibility = View.INVISIBLE

            thirdChoice.visibility = View.VISIBLE
        }, changeSceneDelay)
    }

    fun thirdChoiceJackfruit(view: View) {
        selectedItem = 1
        Toast.makeText(this, "You chose jackfruit!", Toast.LENGTH_SHORT).show()

        val jackfruit = findViewById<ImageButton>(R.id.jackfruit)
        val banana = findViewById<ImageButton>(R.id.banana)
        val back = findViewById<Button>(R.id.back3)
        val confirm = findViewById<Button>(R.id.confirm3)
        val text = findViewById<TextView>(R.id.ingredientText3)
        val jackfruitImage = findViewById<ImageView>(R.id.jackfruitImage)

        buttonFx.start()

        disableButtonsTemporarily()

        fadeOut(jackfruit)
        fadeOut(banana)
        fadeOut(text)

        fadeIn(back)
        fadeIn(confirm)
        fadeIn(jackfruitImage)

        jackfruitImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_top))
    }

    fun thirdChoiceBanana(view: View) {
        selectedItem = 2
        Toast.makeText(this, "You chose banana!", Toast.LENGTH_SHORT).show()

        val jackfruit = findViewById<ImageButton>(R.id.jackfruit)
        val banana = findViewById<ImageButton>(R.id.banana)
        val back = findViewById<Button>(R.id.back3)
        val confirm = findViewById<Button>(R.id.confirm3)
        val text = findViewById<TextView>(R.id.ingredientText3)
        val bananaImage = findViewById<ImageView>(R.id.bananaImage)

        buttonFx.start()

        disableButtonsTemporarily()

        fadeOut(jackfruit)
        fadeOut(banana)
        fadeOut(text)

        fadeIn(back)
        fadeIn(confirm)
        fadeIn(bananaImage)

        bananaImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_top))
    }

    fun back3(view: View) {
        val jackfruit = findViewById<ImageButton>(R.id.jackfruit)
        val banana = findViewById<ImageButton>(R.id.banana)
        val back = findViewById<Button>(R.id.back3)
        val confirm = findViewById<Button>(R.id.confirm3)
        val text = findViewById<TextView>(R.id.ingredientText3)
        val jackfruitImage = findViewById<ImageView>(R.id.jackfruitImage)
        val bananaImage = findViewById<ImageView>(R.id.bananaImage)

        whooshFx.start()

        disableButtonsTemporarily()

        fadeOut(back)
        fadeOut(confirm)

        fadeIn(jackfruit)
        fadeIn(banana)
        fadeIn(text)

        if (selectedItem == 1) {
            jackfruitImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_back))
            jackfruitImage.visibility = View.INVISIBLE
        }
        else {
            bananaImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_back))
            bananaImage.visibility = View.INVISIBLE
        }
    }

    //
    // confirm3 has different animations
    //

    fun confirm3(view: View) {
        val thirdChoice = findViewById<LinearLayout>(R.id.thirdChoice)
        val resultPage = findViewById<LinearLayout>(R.id.resultPage)
        val bowlImage = findViewById<ImageView>(R.id.bowlImage)
        val jackfruitImage = findViewById<ImageView>(R.id.jackfruitImage)
        val bananaImage = findViewById<ImageView>(R.id.bananaImage)

        selectedDessert += selectedItem.toString()

        disableButtonsTemporarily()

        fadeOut(thirdChoice)

        bowlImage.visibility = View.VISIBLE
        bowlImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))

        if (selectedItem == 1) {
            val delayMillis = 1000L
            Handler().postDelayed({
                jackfruitImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.drop_in_bowl))
                jackfruitImage.visibility = View.GONE
            }, delayMillis)
        } else {
            val delayMillis = 1000L
            Handler().postDelayed({
                bananaImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.drop_in_bowl))
                bananaImage.visibility = View.GONE
            }, delayMillis)
        }

        Handler().postDelayed({
            dropFx.start()
        }, 1500L)

        // adds a shake animation to the bowl
        val shakeDelay = 2000L
        Handler().postDelayed({
            bowlImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
            stirFx.start()

            // this changes the image of the bowl to create a simple animation
            val images = listOf(
                R.drawable.bowlspoonleft,
                R.drawable.bowlspoonmid,
                R.drawable.bowlspoonright
            )

            var index = 0 // the index list of the images
            var count = 0 // the current count for the loop
            val handler = Handler()
            val runnable = object : Runnable {
                override fun run() {
                    bowlImage.setImageResource(images[index]) // sets the image to current index
                    index = (index + 1) % images.size // an operation to loop between all images
                    count++ // adds 1 to the count

                    if (count < 20) { // if count is less than 20 will start the loop again
                        handler.postDelayed(this, 125L)
                    } else { // else it will set back to the original image
                        bowlImage.setImageResource(R.drawable.emptybowl)
                        stirFx.pause()
                    }
                }
            }
            handler.post(runnable)
        }, shakeDelay)

        val bowlMoveLeftDelay = 5000L
        Handler().postDelayed({
            bowlImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_to_left))
            bowlImage.visibility = View.GONE
        }, bowlMoveLeftDelay)

        val changeSceneDelay = 6000L
        Handler().postDelayed({
            val resultDessert = findViewById<TextView>(R.id.resultDessert)
            whooshFx.start()

            when (selectedDessert) { // depending on the combination, it will slide the dessert onto screen
                "111" -> {
                    resultDessert.text = "Puto Bumbong!"
                    val item = findViewById<ImageView>(R.id.puto)
                    item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))
                    item.visibility = View.VISIBLE
                }

                "211" -> {
                    resultDessert.text = "Ube Halaya!"
                    val item = findViewById<ImageView>(R.id.ubeHalaya)
                    item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))
                    item.visibility = View.VISIBLE
                }

                "121" -> {
                    resultDessert.text = "Ginataang Bilo-Bilo!"
                    val item = findViewById<ImageView>(R.id.biloBilo)
                    item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))
                    item.visibility = View.VISIBLE
                }

                "221" -> {
                    resultDessert.text = "Ginataang Monggo!"
                    val item = findViewById<ImageView>(R.id.ginataangMonggo)
                    item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))
                    item.visibility = View.VISIBLE
                }

                "112" -> {
                    resultDessert.text = "Bibingka!"
                    val item = findViewById<ImageView>(R.id.bibingka)
                    item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))
                    item.visibility = View.VISIBLE
                }

                "122" -> {
                    resultDessert.text = "Biko!"
                    val item = findViewById<ImageView>(R.id.biko)
                    item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))
                    item.visibility = View.VISIBLE
                }

                "212" -> {
                    resultDessert.text = "Minatamis na Saging!"
                    val item = findViewById<ImageView>(R.id.saging)
                    item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))
                    item.visibility = View.VISIBLE
                }

                "222" -> {
                    resultDessert.text = "Pinaypay!"
                    val item = findViewById<ImageView>(R.id.pinaypay)
                    item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))
                    item.visibility = View.VISIBLE
                }
            }
            resultPage.visibility = View.VISIBLE // changes scene
        }, changeSceneDelay)
    }

    // function to return to the main menu
    fun mainMenuPage(view: View) {
        selectedDessert = "" // resets the selected dessert variable

        val resultPage = findViewById<LinearLayout>(R.id.resultPage)
        val menuLayout = findViewById<LinearLayout>(R.id.menuLayout)
        val background = findViewById<LinearLayout>(R.id.background)
        val item = findViewById<ImageView>(R.id.puto)
        val ubeHalaya = findViewById<ImageView>(R.id.ubeHalaya)
        val biloBilo = findViewById<ImageView>(R.id.biloBilo)
        val ginataangMonggo = findViewById<ImageView>(R.id.ginataangMonggo)
        val bibingka = findViewById<ImageView>(R.id.bibingka)
        val biko = findViewById<ImageView>(R.id.biko)
        val saging = findViewById<ImageView>(R.id.saging)
        val pinaypay = findViewById<ImageView>(R.id.pinaypay)

        disableButtonsTemporarily() // disables buttons

        background.setBackgroundResource(R.drawable.background)
        item.visibility = View.GONE
        ubeHalaya.visibility = View.GONE
        biloBilo.visibility = View.GONE
        ginataangMonggo.visibility = View.GONE
        bibingka.visibility = View.GONE
        biko.visibility = View.GONE
        saging.visibility = View.GONE
        pinaypay.visibility = View.GONE
        resultPage.visibility = View.GONE
        menuLayout.visibility = View.VISIBLE
    }

    // function to showcase the information of the chosen dessert
    fun infoButton(view: View) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(R.drawable.popupbg)
        dialog.setContentView(R.layout.informationpopup)

        disableButtonsTemporarily()

        // changes the text of the info depending on the selected dessert
        val text = dialog.findViewById<TextView>(R.id.informationTextView)
        when (selectedDessert) {
            "111" -> {
                text.text = "Puto bumbong is a type of steamed purple rice cake, this variation contains purple yams and jackfruits. A traditional Filipino dessert enjoyed especially during the Christmas season."
            }
            "211" -> {
                text.text = "Ube Halaya is a sweet and creamy dessert with a beautiful purple colour, made by combining purple yams and coconut milk. Ube halaya can be used as a filling for buns, breads and other pastries."
            }
            "121" -> {
                text.text = "Ginataang Bilo-Bilo is a popular dessert or snack made with glutionous rice balls and an assortment of toppings, this variation has mung beans to add texture and flavour."
            }
            "221" -> {
                text.text = "Ginataang Monggo is a variation of Bilo-Bilo, instead made with toasted mung beans and a variety of toppings like jackfruit. It can be enjoyed hot or cold depending on your preference."
            }
            "112" -> {
                text.text = "Bibingka is a well-loved Filipino rice cake that can be made with many ingredients. It is known for its soft and spongy texture and variety of toppings like bananas and jackfruit."
            }
            "122" -> {
                text.text = "Biko is a sweet and sticky rice cake, a well-loved Filipino dessert that's topped with brown sugar syrup. It is usually wrapped in banana leaves to add flavour and can be eaten with fruit."
            }
            "212" -> {
                text.text = "Minatamis na Saging is a classic Filipino dessert that means \"sweetened bananas\". It is made with ripe saba bananas cooked in a sweet syrup, this variation uses purple yam syrup."
            }
            "222" -> {
                text.text = "Pinaypay, which means \"fanned\" in Tagalog and Cebuano, is a fried banana fritter coated in a sweet syrup, which could be coconut or brown sugar. This variation is topped with mung beans to add texture."
            }
        }

        val skipTextView = dialog.findViewById<TextView>(R.id.skipInfo)

        skipTextView.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }



    // function that disables all the buttons temporarily for
    private fun disableButtonsTemporarily() {
        val start = findViewById<Button>(R.id.startButton)
        val tutorial = findViewById<Button>(R.id.tutorialButton)
        val menu = findViewById<Button>(R.id.mainMenu)
        val info = findViewById<Button>(R.id.infoButton)

        val rice = findViewById<ImageButton>(R.id.rice)
        val coconut = findViewById<ImageButton>(R.id.coconut)
        val yam = findViewById<ImageButton>(R.id.yam)
        val bean = findViewById<ImageButton>(R.id.bean)
        val jackfruit = findViewById<ImageButton>(R.id.jackfruit)
        val banana = findViewById<ImageButton>(R.id.banana)

        val back1 = findViewById<Button>(R.id.back1)
        val confirm1 = findViewById<Button>(R.id.confirm1)
        val back2 = findViewById<Button>(R.id.back2)
        val confirm2 = findViewById<Button>(R.id.confirm2)
        val back3 = findViewById<Button>(R.id.back3)
        val confirm3 = findViewById<Button>(R.id.confirm3)

        start.isEnabled = false
        tutorial.isEnabled = false
        menu.isEnabled = false
        info.isEnabled = false

        rice.isEnabled = false
        coconut.isEnabled = false
        yam.isEnabled = false
        bean.isEnabled = false
        jackfruit.isEnabled = false
        banana.isEnabled = false

        back1.isEnabled = false
        confirm1.isEnabled = false
        back2.isEnabled = false
        confirm2.isEnabled = false
        back2.isEnabled = false
        confirm2.isEnabled = false
        back3.isEnabled = false
        confirm3.isEnabled = false

        val delayMillis = 500L
        Handler().postDelayed({
            start.isEnabled = true
            tutorial.isEnabled = true
            menu.isEnabled = true
            info.isEnabled = true

            rice.isEnabled = true
            coconut.isEnabled = true
            yam.isEnabled = true
            bean.isEnabled = true
            jackfruit.isEnabled = true
            banana.isEnabled = true

            back1.isEnabled = true
            confirm1.isEnabled = true
            back2.isEnabled = true
            confirm2.isEnabled = true
            back2.isEnabled = true
            confirm2.isEnabled = true
            back3.isEnabled = true
            confirm3.isEnabled = true
        }, delayMillis)
    }

    // fade out animation
    private fun fadeOut(view: View) {
        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = 500
        view.startAnimation(fadeOut)
        view.visibility = View.INVISIBLE
    }

    // fade in animation
    private fun fadeIn(view: View) {
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 500
        view.startAnimation(fadeIn)
        view.visibility = View.VISIBLE
    }
}