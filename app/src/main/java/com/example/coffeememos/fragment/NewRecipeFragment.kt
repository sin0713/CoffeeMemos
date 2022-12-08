package com.example.coffeememos.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeememos.CoffeeMemosApplication
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.databinding.FragmentNewRecipeBinding
import com.example.coffeememos.dialog.BasicDialogFragment
import com.example.coffeememos.dialog.ListDialogFragment
import com.example.coffeememos.listener.SimpleTextWatcher
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.state.InputType
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.state.ProcessState
import com.example.coffeememos.utilities.AnimUtil
import com.example.coffeememos.utilities.DateUtil
import com.example.coffeememos.utilities.ViewUtil
import com.example.coffeememos.viewModel.MainViewModel
import com.example.coffeememos.viewModel.NewRecipeViewModel
import com.example.coffeememos.viewModel.NewRecipeViewModelFactory
import com.google.android.material.snackbar.Snackbar

class NewRecipeFragment :
    Fragment(),
    View.OnClickListener {
    // viewBinding
    private  var _binding: FragmentNewRecipeBinding? = null
    private val binding
        get() = _binding!!

    // アクティビティのコンテキストを保持
    private var mContext: Context? = null

    private val  viewModel: NewRecipeViewModel by viewModels {
        // viewModelの初期化
        val db = ((context?.applicationContext) as CoffeeMemosApplication).database
        NewRecipeViewModelFactory(db.recipeDao(), db.beanDao(), db.tasteDao())
    }

    // 共有viewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    private val safeArgs: NewRecipeFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initialize(
            RatingManager(),
            safeArgs.preInfusionTimeInputType,
            safeArgs.extractionTimeInputType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header セッティング
        binding.header.headerTitle.text = getString(R.string.new_recipe)
        binding.header.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        // お気に入り 監視処理
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else binding.header.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        binding.header.favoriteBtn.setOnClickListener {
            if (viewModel.isFavorite.value == true) viewModel.setFavoriteFlag(false)
            else viewModel.setFavoriteFlag(true)
        }


        // 蒸らし時間 監視処理
        viewModel.preInfusionTimeInputType.observe(viewLifecycleOwner) { inputType ->
            changeVisibility(inputType, binding.preInfusionTimeEditText, binding.preInfusionTimeTextView)
        }

        mainViewModel.formattedPreInfusionTime.observe(viewLifecycleOwner) { preInfusionTime ->
            binding.preInfusionTimeTextView.text = preInfusionTime
        }

        binding.changePreInfusionInputTypeIcon.setOnClickListener {
            if (viewModel.preInfusionTimeInputType.value == InputType.AUTO)
                viewModel.setPreInfusionTimeInputType(InputType.MANUAL)
            else
                viewModel.setPreInfusionTimeInputType(InputType.AUTO)
        }


        // 抽出時間 監視処理
        viewModel.extractionTimeInputType.observe(viewLifecycleOwner) { inputType ->
            changeVisibility(inputType, binding.extractionTimeWrapper, binding.extractionTimeTextView)
        }

        mainViewModel.formattedExtractionTime.observe(viewLifecycleOwner) { extractionTime ->
            binding.extractionTimeTextView.text = extractionTime
        }

        binding.changeExtractionInputTypeIcon.setOnClickListener {
            if (viewModel.extractionTimeInputType.value == InputType.AUTO)
                viewModel.setExtractionTimeInputType(InputType.MANUAL)
            else
                viewModel.setExtractionTimeInputType(InputType.AUTO)
        }


        // TextChangeListener セット
        binding.sourValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setSour(editable.toString())
            }
        })
        binding.bitterValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setBitter(editable.toString())
            }
        })
        binding.sweetValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setSweet(editable.toString())
            }
        })
        binding.flavorValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setFlavor(editable.toString())
            }
        })
        binding.richValue.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setRich(editable.toString())
            }
        })

        binding.toolEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setTool(editable.toString())
            }
        })
        binding.amountBeanEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setAmountBeans(editable.toString())
            }
        })
        binding.temperatureEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setTemperature(editable.toString())
            }
        })
        binding.preInfusionTimeEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setPreInfusionTime(editable.toString())
            }
        })
        binding.extractionTimeMinuteEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setExtractionTimeMinutes(editable.toString())
            }
        })
        binding.extractionTimeSecondsEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setExtractionTimeSeconds(editable.toString())
            }
        })
        binding.amountExtractionEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setAmountExtraction(editable.toString())
            }
        })
        binding.commentEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.setComment(editable.toString())
            }
        })

        // 編集ダイアログ
        // Roast
        viewModel.currentRoast.observe(viewLifecycleOwner) { roast ->
            binding.roastTextView.text = Constants.roastList[roast]
        }
        // 編集アイコンクリックリスナ―
        binding.selectRoastBtn.setOnClickListener {
            ListDialogFragment
                .create(viewModel.currentRoast.value!!, getString(R.string.edit_roast), "updateRoast", Constants.roastList.toTypedArray())
                .show(childFragmentManager, ListDialogFragment::class.simpleName)
        }
        // RoastDialogからの結果を受信
        childFragmentManager.setFragmentResultListener("updateRoast", viewLifecycleOwner) {_, bundle ->
            viewModel.setRoast(bundle.getInt("newIndex"))
        }

        // Grind Size
        viewModel.currentGrind.observe(viewLifecycleOwner) { grind ->
            binding.grindTextView.text = Constants.grindSizeList[grind]
        }
        // 編集アイコンクリックリスナ―
        binding.selectGrindBtn.setOnClickListener {
            ListDialogFragment
                .create(viewModel.currentGrind.value!!, getString(R.string.edit_grind), "updateGrind", Constants.grindSizeList.toTypedArray())
                .show(childFragmentManager, ListDialogFragment::class.simpleName)
        }
        // GrindDialogからの結果を受信
        childFragmentManager.setFragmentResultListener("updateGrind", viewLifecycleOwner) {_, bundle ->
            viewModel.setGrind(bundle.getInt("newIndex"))
        }


        // コーヒー豆選択画面へ遷移
        binding.selectBeanBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.selectBeanFragment)
        }
        // コーヒー豆監視
        mainViewModel.selectedBean.observe(viewLifecycleOwner) { bean ->
            val beanContainer = binding.beanContainer
            beanContainer.country.text     = bean.country
            beanContainer.district.text    = bean.district
            beanContainer.createdAt.text   = DateUtil.formatEpochTimeMills(bean.createdAt, DateUtil.pattern)
            beanContainer.storeName.text   = bean.store
            beanContainer.processName.text = Constants.processList[bean.process]
            beanContainer.speciesName.text = bean.species
            beanContainer.rating.text      = getString(R.string.rate_decimal, bean.rating.toString())
            beanContainer.ratingWrapper.visibility = View.VISIBLE

            ViewUtil.setRecipeTag(beanContainer.farm, bean.farm)
            ViewUtil.setRecipeTag(beanContainer.district, bean.district)
            ViewUtil.setFavoriteIcon(beanContainer.favoriteIcon, bean.isFavorite)
        }


        // Rating
        val recipeStarViewList: List<ImageView> = listOf(
            binding.starFirst,
            binding.starSecond,
            binding.starThird,
            binding.starFourth,
            binding.starFifth,
        )
        // rating ★Viewの状態監視処理
        viewModel.recipeStarList.observe(viewLifecycleOwner) { starList ->
            for ((index, star) in starList.withIndex()) {
                if (star.state == RatingManager.StarState.LIGHT) recipeStarViewList[index].setImageResource(R.drawable.ic_baseline_star_beige_light_24)
                else recipeStarViewList[index].setImageResource(R.drawable.ic_baseline_star_grey)
            }
        }
        // Ratingの値 監視処理
        viewModel.recipeCurrentRating.observe(viewLifecycleOwner) { currentRating ->
            binding.beanRating.text = getString(R.string.rate_decimal, currentRating.toString())
        }
        // ★画像のクリックリスナーセット
        binding.starFirst.setOnClickListener(this)
        binding.starSecond.setOnClickListener(this)
        binding.starThird.setOnClickListener(this)
        binding.starFourth.setOnClickListener(this)
        binding.starFifth.setOnClickListener(this)


        // progressBar 監視処理
        viewModel.processState.observe(viewLifecycleOwner) { state ->
            when(state) {
                ProcessState.BEFORE_PROCESSING -> {
                    binding.progressBar.visibility = View.GONE
                }
                ProcessState.PROCESSING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                ProcessState.FINISH_PROCESSING -> {
                    binding.progressBar.visibility = View.GONE
                    setFragmentResult("createRecipe", Bundle())
                    findNavController().navigate(R.id.action_back_to_homeRecipeFragment)
                }
            }
        }


        //fab 監視処理
        viewModel.isMenuOpened.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            when(state) {
                MenuState.OPEN -> {
                    binding.wholeShadow.visibility = View.VISIBLE
                    binding.menuBtn.setImageResource(R.drawable.ic_baseline_close_24)
                    enableBtn(binding.timeBtn, binding.saveBtn)
                    AnimUtil.fadeInAnimation(binding.timeBtn, 500L)
                    AnimUtil.fadeInAnimation(binding.saveBtn, 500L)
                }
                MenuState.CLOSE -> {
                    binding.wholeShadow.visibility = View.GONE
                    binding.menuBtn.setImageResource(R.drawable.ic_baseline_menu_24)
                    disableBtn(binding.timeBtn, binding.saveBtn)
                    AnimUtil.fadeOutAnimation(binding.timeBtn, 500L)
                    AnimUtil.fadeOutAnimation(binding.saveBtn, 500L)
                }
            }
        }
        // menu クリックリスナー
        binding.menuBtn.setOnClickListener {
            when(viewModel.isMenuOpened.value) {
                MenuState.OPEN -> viewModel.setMenuOpenedFlag(MenuState.CLOSE)
                MenuState.CLOSE -> viewModel.setMenuOpenedFlag(MenuState.OPEN)
                else -> viewModel.setMenuOpenedFlag(MenuState.OPEN)
            }
        }
        // 計測画面に遷移
        binding.timeBtn.setOnClickListener { view ->
            viewModel.setMenuOpenedFlag(MenuState.CLOSE)
            
            val showTimerAction = NewRecipeFragmentDirections.showTimerAction().apply {
               existsNewRecipeFragment = true
            }
            Navigation.findNavController(view).navigate(showTimerAction)
        }
        // 計測画面からの戻り
        setFragmentResultListener("returnFromTimer") { _, _ ->
            viewModel.setPreInfusionTimeInputType(InputType.AUTO)
            viewModel.setExtractionTimeInputType(InputType.AUTO)
        }
        // レシピ 保存処理
        binding.saveBtn.setOnClickListener {
            mainViewModel.selectedBean.value?.let {  _ ->
                BasicDialogFragment
                    .create(
                        getString(R.string.create_recipe_message),
                        getString(R.string.save),
                        getString(R.string.cancel),
                        "createRecipe")
                    .show(childFragmentManager, BasicDialogFragment::class.simpleName)

                return@setOnClickListener
            }

            viewModel.setMenuOpenedFlag(MenuState.CLOSE)
            Snackbar.make(binding.snackBarPlace, getString(R.string.bean_required_message), Snackbar.LENGTH_SHORT).apply {
                mContext?.let {
                    setTextColor(ContextCompat.getColor(it, R.color.delete_color))
                    getView().setBackgroundColor(ContextCompat.getColor(it, R.color.white))
                }
            }.show()
        }
        childFragmentManager.setFragmentResultListener("createRecipe", viewLifecycleOwner) { _, _ ->
            val beanId = mainViewModel.selectedBean.value!!.id
            viewModel.createNewRecipeAndTaste(
                beanId,
                mainViewModel.preInfusionTime.value!!,
                mainViewModel.extractionTime.value!!
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    // ★画像の共通クリックリスナー
    override fun onClick(starView: View) {
        when(starView.id) {
            R.id.starFirst  -> viewModel.updateRatingState(1)
            R.id.starSecond -> viewModel.updateRatingState(2)
            R.id.starThird  -> viewModel.updateRatingState(3)
            R.id.starFourth -> viewModel.updateRatingState(4)
            R.id.starFifth  -> viewModel.updateRatingState(5)
        }
    }

    private fun changeVisibility(type: InputType, manualView: View, autoView: View) {
        if (type == InputType.AUTO) {
            manualView.visibility = View.INVISIBLE
            autoView.visibility = View.VISIBLE
        } else {
            manualView.visibility = View.VISIBLE
            autoView.visibility = View.GONE
        }
    }

    private fun enableBtn(vararg views: View) {
        for (view in views) { view.isEnabled = true }
    }

    private fun disableBtn(vararg views: View) {
        for (view in views) { view.isEnabled = false }
    }
}